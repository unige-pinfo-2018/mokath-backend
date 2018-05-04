/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.mokath.uniknowledgerestapi.dom.Institution;

/**
 * @author tv0g
 *
 */
@Stateless
public class InstitutionsServiceImpl implements InstitutionsService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void createInstitution(Institution i) {
		em.persist(i);
	}

}
