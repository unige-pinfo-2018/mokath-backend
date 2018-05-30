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
 * @author ornela
 * @author zue
 */
public class InstitutionTest {

	@Test
	public void institutionBuilderShouldReturnInstitution() {

		HashSet<String> domains = new HashSet<String>();
		Institution institution = new Institution("InsName", "InsLogo", "contact@institution.com", domains);

		Assert.assertEquals(institution.getInstitutionName(), "InsName");
		Assert.assertTrue(institution.getInstitutionName()!= "IsName");
	    
	}
	
	@Test
	public void institutionBuilderShouldReturnContactEmail() {
		HashSet<String> domains = new HashSet<String>();
		Institution institution = new Institution("InsName", "InsLogo", "contact@institution.com", domains);
		Assert.assertFalse(institution.getContactEmail() == "openclassroom@uni.com");
	    
	}
	
	@Test
	public void institutionSetandGetTest() {
		HashSet<String> domains = new HashSet<String>();
		Institution institution = new Institution("InsName", "InsLogo", "contact@institution.com", domains);
		institution.setContactEmail("github@help.com");
		Assert.assertTrue(institution.getContactEmail() == "github@help.com");
	    
	}
		
}
