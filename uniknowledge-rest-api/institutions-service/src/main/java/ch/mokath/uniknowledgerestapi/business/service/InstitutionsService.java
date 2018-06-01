/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.util.List;

import javax.ejb.Local;

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
	void createInstitution(Institution i) throws CustomException;
	
	/**
	 * Get institution from the database
	 * @param id ID of the Institution to get (String)
	 * @return Institution - List of all institutions in the database
	 */
	Institution getInstitution(final String id) throws CustomException;
	
	/**
	 * Get all institutions in the database
	 * @return List<Institution> List of all institutions in the database
	 */
	List<Institution> getInstitutions();
	
	/**
	 * Update institution informations in the database
	 * 
	 * @param i Institution informations to update
	 * @param id ID of the Institution to update (String)
	 * @return Institution Updated institution reflecting database
	 */
	Institution updateInstitution(Institution i,final String id) throws CustomException;

	/**
	 * Delete institution from database
	 * @param id ID of the Institution to update (String)
	 */
	void deleteInstitution(final String id) throws CustomException;
	
	/**
	* Add or remove an administrator to/from an institution
	* @param iid Institution to add/get-all/remove User to/from (String ID)
	* @param uid User to add/get-all/remove to/from Institution (String ID)
	* @param i Institution the administrator will be added to/removed from
	*/
	User addUser(final String uid,final String iid) throws CustomException;
	List<User> getUsers(final String iid) throws CustomException;
	void removeUser(final String uid,final String iid) throws CustomException;
}
