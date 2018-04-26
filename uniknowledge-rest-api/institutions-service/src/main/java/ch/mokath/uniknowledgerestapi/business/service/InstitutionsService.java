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
	 * Create an institution in database
	 * @param i Institution to store in database
	 * @return True iff institution was created in database
	 */
	public void createInstitution(Institution i);

}
