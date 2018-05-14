/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import javax.ejb.Local;
import javax.persistence.EntityExistsException;
import javax.validation.constraints.NotNull;

import ch.mokath.uniknowledgerestapi.dom.Institution;

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
	void createInstitution(@NotNull final Institution i) throws EntityExistsException;
	
	/**
	 * Update institution informations in the database
	 * 
	 * @param i Institution informations to update
	 * @return Institution Updated institution reflecting database
	 */
	Institution updateInstitution(@NotNull final Institution i);

	/**
	 * Delete institution from database
	 * @param i Institution to delete
	 */
	void deleteInstitution(@NotNull final Institution i);
	
}
