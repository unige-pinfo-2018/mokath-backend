/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import javax.ejb.Local;

import ch.mokath.uniknowledgerestapi.dom.Institution;

/**
 * @author tv0g
 *
 */
@Local
public interface InstitutionsService {

	/**
	 * Store an institution to database
	 * @param i Institution to store
	 */
	void createInstitution(Institution i);

}
