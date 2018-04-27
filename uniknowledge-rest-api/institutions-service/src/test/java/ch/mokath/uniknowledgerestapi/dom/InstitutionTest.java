/**
 * 
 */
package ch.mokath.uniknowledgerestapi.dom;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author tv0g
 *
 */
public class InstitutionTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void institutionBuilderShouldReturnInstitution() {

		Institution institution = new Institution.Builder().with($ -> {
			$.institutionName = "InsName";
			$.logoPictureURL = "InsLogo";
			$.contactEmail = "contact@institution.com";
			$.administrators = new HashSet<String>();
			$.askers = new HashSet<String>();
			$.repliers = new HashSet<String>();
		}).build();

		Assert.assertEquals(institution.getInstitutionName(), "InsName");
	}
}
