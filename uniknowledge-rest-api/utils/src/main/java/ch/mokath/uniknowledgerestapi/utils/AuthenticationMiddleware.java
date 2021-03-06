package ch.mokath.uniknowledgerestapi.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Priority;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.container.ContainerRequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private Logger log = LoggerFactory.getLogger(AuthenticationMiddleware.class);
	private DBHelper DBHelper = new DBHelper();

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
		String untrustedToken = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

		int i = untrustedToken.lastIndexOf('.');
		String withoutSignature = untrustedToken.substring(0, i + 1);

		try {
			Jwt<Header, Claims> untrustedClaims = Jwts.parser().parseClaimsJwt(withoutSignature);
			long untrustedUserID = Long.parseLong(untrustedClaims.getBody().getAudience());

			Map<String, Object> untrustedSelectors = new HashMap<String, Object>();
			untrustedSelectors.put("id", untrustedUserID);
			Optional<User> matchedUntrustedUser = DBHelper.getEntityFromFields(untrustedSelectors, User.class, em);

			if (matchedUntrustedUser.isPresent()) {

				User u = matchedUntrustedUser.get();

				Token trustedToken = em.createQuery("SELECT x FROM Token x WHERE x.user = ?1", Token.class)
						.setParameter(1, u).getSingleResult();
				try {
					Claims tokenClaims = validateToken(untrustedToken, trustedToken.getSigningKey());

					// Now that the token is validated and trusted, we can query the user and pass the informations in the context
					Map<String, Object> trustedSelectors = new HashMap<String, Object>();
					trustedSelectors.put("id", Long.parseLong(tokenClaims.getAudience()));

					Optional<User> matchedTrustedUser = DBHelper.getEntityFromFields(trustedSelectors, User.class, em);

					if (matchedTrustedUser.isPresent()) {
						log.info(matchedTrustedUser.get().toString());
						requestContext.setProperty("user", matchedTrustedUser.get());
						requestContext.setProperty("token", trustedToken);
					} else {
						requestContext.abortWith(CustomErrorResponse.ERROR_OCCURED.getHTTPResponse());
					}
				} catch (Exception e) {
					log.error(e.getMessage());
					requestContext.abortWith(CustomErrorResponse.INVALID_TOKEN.getHTTPResponse());
				}
			} else {
				requestContext.abortWith(CustomErrorResponse.INVALID_TOKEN.getHTTPResponse());
			}

		} catch (Exception e) {
			log.error("Exception thrown during the authenticatin process : " + e.toString());
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
		Claims claims = Jwts.parser().setSigningKey(CustomDecoder.fromBase64(base64SigningKey)).parseClaimsJws(token)
				.getBody();

		return claims;
	}
}
