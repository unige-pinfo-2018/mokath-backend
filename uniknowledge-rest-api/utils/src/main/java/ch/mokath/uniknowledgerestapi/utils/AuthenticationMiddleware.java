package ch.mokath.uniknowledgerestapi.utils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.annotation.Priority;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import ch.mokath.uniknowledgerestapi.dom.Institution;
import ch.mokath.uniknowledgerestapi.dom.Token;
import ch.mokath.uniknowledgerestapi.dom.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationMiddleware implements ContainerRequestFilter {

	@PersistenceContext
	private EntityManager em;

	private static final String AUTHENTICATION_SCHEME = "Bearer";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		// Get the Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Validate the Authorization header
		if (!isTokenBasedAuthentication(authorizationHeader)) {
			requestContext.abortWith(CustomErrorResponse.INVALID_TOKEN.getHTTPResponse());
			return;
		}

		// Extract the token from the Authorization header
		String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

		int i = token.lastIndexOf('.');
		String withoutSignature = token.substring(0, i+1);
		
		try {
			Jwt<Header,Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);
			long userID = Integer.parseInt(untrusted.getBody().getAudience());

			Optional<List<User>> matchedUsers = getUsersFrom("id", userID);
			
			if (matchedUsers.isPresent()) {
				
				User u = matchedUsers.get().get(0);

				Token resToken = em.createQuery("SELECT x FROM Token x WHERE x.user = ?1", Token.class)
						.setParameter(1, u).getSingleResult();
				try {
					Claims tokenClaims = validateToken(token, resToken.getSigningKey());
			        requestContext.setProperty("userID", Long.parseLong(tokenClaims.getAudience()));
			        requestContext.setProperty("token", resToken);
				} catch (Exception e) {
					requestContext.abortWith(CustomErrorResponse.INVALID_TOKEN.getHTTPResponse());
				}
			}

		} catch(Exception e) {
			requestContext.abortWith(CustomErrorResponse.INVALID_TOKEN.getHTTPResponse());
		}
	}

	/**
	 * Check if the Authorization Header is valid
	 * 
	 * @param authorizationHeader
	 *            Authorization to use
	 * @return true iff Authorization header matches AUTHENTICATION_SCHEME Specs
	 */
	private boolean isTokenBasedAuthentication(String authorizationHeader) {
		return authorizationHeader != null
				&& authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
	}

	private Claims validateToken(String token, String base64SigningKey) throws Exception {

		// This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims = Jwts.parser().setSigningKey(CustomDecoder.fromBase64(base64SigningKey))
				.parseClaimsJws(token).getBody();
		
		return claims;
	}
	
	private <T> Optional<List<User>> getUsersFrom(String field, T value) {

		// Create the Critera Builder
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// Link Query to User Class
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> from = criteriaQuery.from(User.class);

		// Modify and create the query to match given field/value pairs entries
		criteriaQuery.where(criteriaBuilder.equal(from.get(field), value));
		TypedQuery<User> finalQuery = em.createQuery(criteriaQuery);

		// Execute SELECT request on previous defined query predicates
		List<User> matchedUsers = finalQuery.getResultList();
		// If users list is not empty, return list of users wrapped in Optional object
		// else, return an empty Optional object
		return matchedUsers.isEmpty() ? Optional.empty() : Optional.of(matchedUsers);
	}
}