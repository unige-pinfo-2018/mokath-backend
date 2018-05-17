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

	@Test
	public void institutionBuilderShouldReturnInstitution() {

		Institution institution = new Institution.Builder().with($ -> {
			$.institutionName = "InsName";
			$.logoPictureURL = "InsLogo";
			$.contactEmail = "contact@institution.com";
			$.domains = new HashSet<String>();
//			$.administrators = new HashSet<User>();
//			$.askers = new HashSet<User>();
//			$.repliers = new HashSet<User>();
		}).build();

		Assert.assertEquals(institution.getInstitutionName(), "InsName");
	}
}
