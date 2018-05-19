/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityExistsException;
import javax.validation.constraints.NotNull;

import ch.mokath.uniknowledgerestapi.dom.Institution;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.CustomException;

/**
 * @author tv0g
 * @author zue
 */
@Local
public interface InstitutionsService {

	/**
	 * Create an institution in the database
	 * @param i Institution to store
	 */
	void createInstitution(@NotNull final Institution i) throws CustomException;
	
	/**
	 * Update institution informations in the database
	 * 
	 * @param i Institution informations to update
	 * @return Institution Updated institution reflecting database
	 */
	Institution updateInstitution(@NotNull final Institution i) throws CustomException;

	/**
	 * Delete institution from database
	 * @param i Institution to delete
	 */
	void deleteInstitution(@NotNull final Institution i) throws CustomException;
	
	/**
	* Add or remove an administrator to/from an institution
	* @param administrator User to add/remove as administrator
	* @param i Institution the administrator will be added to/removed from
	*/
	void addUser(User u,Institution i);
	boolean removeUser(User u,Institution i);
	String getUsers(Institution i);
}
