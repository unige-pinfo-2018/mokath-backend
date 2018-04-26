/**
 * 
 */
package ch.mokath.uniknowledgerestapi.dom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.xml.registry.infomodel.User;

import java.util.HashSet;
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

		Institution institution = new Institution.Builder()
				.with($ -> {
					$.name = "InsName";
					$.logoPictureURL = "InsLogo";
					$.contactEmail = "contact@institution.com";
					$.administrators = new HashSet<String>();
					$.askers = new HashSet<String>();
					$.repliers = new HashSet<String>();
				})
				.build();

		Assert.assertEquals(institution.getName(),"InsName");
	}
}
