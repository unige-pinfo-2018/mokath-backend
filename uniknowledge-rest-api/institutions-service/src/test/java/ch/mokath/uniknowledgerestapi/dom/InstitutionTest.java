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
//			$.administrators = new HashSet<String>();
//			$.askers = new HashSet<String>();
//			$.repliers = new HashSet<String>();
		}).build();

		Assert.assertEquals(institution.getInstitutionName(), "InsName");
		Assert.assertTrue(institution.getInstitutionName()!= "IsName");
		Assert.assertFalse(institution.getContactEmail() == "openclassroom@uni.com");
	    
	}
	
	@Test
	public void institutionBuilderShouldntDetectWrongEmail() {

		Institution institution = new Institution.Builder().with($ -> {
			$.institutionName = "InsName";
			$.logoPictureURL = "InsLogo";
			$.contactEmail = "contactinstitution.com";
			$.domains = new HashSet<String>();
//			$.administrators = new HashSet<String>();
//			$.askers = new HashSet<String>();
//			$.repliers = new HashSet<String>();
		}).build();
	
		Assert.assertTrue(institution.getContactEmail() == "contactinstitution.com");
		institution.setContactEmail("github@help.com");
		Assert.assertTrue(institution.getContactEmail() == "github@help.com");
		
	}
	
	
}
