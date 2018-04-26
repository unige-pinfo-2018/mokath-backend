/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.util.Optional;
import java.util.Set;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.StreamingOutput;

import ch.mokath.uniknowledgerestapi.dom.User;

/**
 * @author tv0g
 *
 */
@Local
public interface UsersService {
	
	Optional<Set<User>> getAllUsers();
	Optional<User> getUserWithJWT(String JWToken);
	Boolean createUser(@NotNull final User user);
	Boolean deleteUser(@NotNull final String userUUID);
	Optional<User> updateUserInformations(@NotNull final User user);
	Optional<Set<User>> getRepliersFromInstitution(@NotNull final User user);
	
}
