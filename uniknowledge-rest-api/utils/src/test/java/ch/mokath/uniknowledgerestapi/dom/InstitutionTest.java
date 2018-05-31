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
	//equal test
	@Test
	public void institutionTestOfEqualFunction() {

		HashSet<String> domains = new HashSet<String>();
		Institution institution = new Institution("InsName", "InsLogo", "contact@institution.com", domains);
		Institution otherInstitution = institution;
		Assert.assertEquals(institution.equals(otherInstitution), true);		
	    
	}
	@Test
	public void institutionTestOfEqualFunctionForTwoInstitution() {

		HashSet<String> domains = new HashSet<String>();
		
		Institution institution = new Institution("InsName", "InsLogo", "contact@institution.com", domains);
		Institution otherInstitution = null;

		Assert.assertEquals(institution.equals(otherInstitution), false);		
	    
	}
	@Test
	public void institutionTestOfEqualFunctionTwoClass() {

		HashSet<String> domains = new HashSet<String>();
		
		Institution institution =new Institution(null, "InsLogo", "contact@institution.com", domains);
		User user = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
		Assert.assertEquals(institution.equals(user), false);		
	    
	}
	
	
	@Test
	public void institutionTestOfEqualFunctionByNameNull() {

		HashSet<String> domains = new HashSet<String>();
		Institution institution = new Institution(null, "InsLogo", "contact@institution.com", domains);
		Institution otherInstitution = new Institution("InsName", "InsLogo", "contact@institution.com", domains);

		Assert.assertEquals(institution.equals(otherInstitution), false);		
	    
	}
	@Test
	public void institutionTestOfEqualFunctionByNameDifferent() {

		HashSet<String> domains = new HashSet<String>();
		Institution institution = new Institution("InsName", "InsLogo", "contact@institution.com", domains);
		Institution otherInstitution = new Institution("OtherInsName", "InsLogo", "contact@institution.com", domains);

		Assert.assertEquals(institution.equals(otherInstitution), false);		
	    
	}
	
	@Test
	public void institutionTestOfEqualFunctionByContact() {

		HashSet<String> domains = new HashSet<String>();
		Institution institution = new Institution("InsName", "InsLogo", null, domains);
		Institution otherInstitution = new Institution("InsName", "InsLogo", "contact@institution.com", domains);

		Assert.assertEquals(institution.equals(otherInstitution), false);		
	    
	}
	@Test
	public void institutionTestOfEqualFunctionByContactDifferent() {

		HashSet<String> domains = new HashSet<String>();
		Institution institution = new Institution("InsName", "InsLogo", "contact@institution.com", domains);
		Institution otherInstitution = new Institution("InsName", "InsLogo", "otherContact@institution.com", domains);

		Assert.assertEquals(institution.equals(otherInstitution), false);		
	    
	}
	@Test
	public void institutionTestOfEqualFunctionByLogo() {

		HashSet<String> domains = new HashSet<String>();
		Institution institution = new Institution("InsName", null,"contact@institution.com", domains);
		Institution otherInstitution = new Institution("InsName", "InsLogo", "contact@institution.com", domains);

		Assert.assertEquals(institution.equals(otherInstitution), false);		
	    
	}
	@Test
	public void institutionTestOfEqualFunctionByLogoDifferent() {

		HashSet<String> domains = new HashSet<String>();
		Institution institution = new Institution("InsName", "InsLogo","contact@institution.com", domains);
		Institution otherInstitution = new Institution("InsName", "OtherInsLogo", "contact@institution.com", domains);

		Assert.assertEquals(institution.equals(otherInstitution), false);		
	    
	}
	@Test
	public void institutionTestOfEqualFunctionByDomain() {

		HashSet<String> domains = new HashSet<String>();
		
		Institution institution = new Institution("InsName", "InsLogo","contact@institution.com", null);
		Institution otherInstitution = new Institution("InsName", "InsLogo", "contact@institution.com", domains);

		Assert.assertEquals(institution.equals(otherInstitution), false);
		institution.setDomains(domains);
		Assert.assertEquals(institution.equals(otherInstitution), true);
	    
	}
	@Test
	public void institutionTestOfGetDomains() {

		HashSet<String> domains = new HashSet<String>();
		
		Institution institution = new Institution("InsName", "InsLogo","contact@institution.com", domains);
		
		Assert.assertEquals(institution.getDomains(),domains);
	    
	}
	@Test
	public void institutionTestOfGetLogo() {

		HashSet<String> domains = new HashSet<String>();
		
		Institution institution = new Institution("InsName", "InsLogo","contact@institution.com", domains);
		
		Assert.assertEquals(institution.getLogoPictureURL(),"InsLogo");
	    
	}
	@Test
	public void institutionTestOfGetContact() {

		HashSet<String> domains = new HashSet<String>();
		
		Institution institution = new Institution("InsName", "InsLogo","contact@institution.com", domains);
		
		Assert.assertEquals(institution.getContactEmail(),"contact@institution.com");
	    
	}
	@Test
	public void institutionTestOfGetUsers() {

		HashSet<String> domains = new HashSet<String>();
		
		Institution institution = new Institution("InsName", "InsLogo","contact@institution.com", domains);
		
		User user = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
		institution.addUser(user);
		
		Assert.assertEquals(institution.getUsers().size(),1);
		institution.removeUser(user);
		Assert.assertEquals(institution.getUsers().size(),0);
	    
	}
	@Test
	public void institutionTestOfSetLogo() {

		HashSet<String> domains = new HashSet<String>();
		
		Institution institution = new Institution("InsName", "InsLogo","contact@institution.com", domains);
		institution.setLogoPictureURL("OtherInsLogo");
		Assert.assertEquals(institution.getLogoPictureURL(),"OtherInsLogo");
	    
	}
	@Test
	public void institutionTestOfSetName() {

		HashSet<String> domains = new HashSet<String>();
		
		Institution institution = new Institution("InsName", "InsLogo","contact@institution.com", domains);
		institution.setInstitutionName("OtherInsName");
		Assert.assertFalse(institution.getInstitutionName()=="InsName");
	    
	}
	@Test
	public void institutionTestOfSetId() {

		HashSet<String> domains = new HashSet<String>();
		
		Institution institution = new Institution("InsName", "InsLogo","contact@institution.com", domains);
		institution.setId(6L);
		Assert.assertFalse(institution.getId()==3L);
	    
	}
	@Test
	public void institutionTestBuilderMethod() {
		HashSet<String> domains = new HashSet<String>();
		Institution institution = new Institution("InsName", "InsLogo","contact@institution.com", domains);		
			
		
		Institution.Builder institutionBuilder = new Institution.Builder();
		institutionBuilder.institutionName = "InsName";
		institutionBuilder.logoPictureURL = "InsLogo";
		institutionBuilder.contactEmail = "contact@institution.com";
		institutionBuilder.domains =domains;
		Institution institutionFromBuilder = institutionBuilder.build(); 		
		Assert.assertEquals(institution.equals(institutionFromBuilder),true);
	}
	@Test
	public void institutionTestHashMethod() {
		HashSet<String> domains = new HashSet<String>();
		HashSet<String> otherDomains = new HashSet<String>();
		
		Institution institution = new Institution("InsName", "InsLogo","contact@institution.com", domains);
		Institution otherInstitution = new Institution("OtherInsName", "OtherInsLogo","othercontact@institution.com", otherDomains);
					
		Assert.assertFalse(institution.hashCode()==otherInstitution.hashCode());
	}
	@Test
	public void institutionTestHashMethodOfEqualInstitution() {
		HashSet<String> domains = new HashSet<String>();
		
		Institution institution = new Institution("InsName", "InsLogo","contact@institution.com", domains);
		Institution otherInstitution = new Institution("InsName", "InsLogo","contact@institution.com", domains);
					
		Assert.assertTrue(institution.hashCode()==otherInstitution.hashCode());
	}
	@Test
	public void institutionTestHashMethodOfNullInstitution() {
		HashSet<String> domains = new HashSet<String>();
		
		Institution institution = new Institution(null, null,null, null);
		Institution otherInstitution = new Institution(null,null,null,null);
					
		Assert.assertTrue(institution.hashCode()==otherInstitution.hashCode());
	}
	@Test
	public void institutionTestTostringMethod() {
		HashSet<String> domains = new HashSet<String>();
		HashSet<String> otherDomains = new HashSet<String>();
		
		Institution institution = new Institution("InsName", "InsLogo","contact@institution.com", domains);
		Institution otherInstitution = new Institution("OtherInsName", "OtherInsLogo","othercontact@institution.com", otherDomains);
					
		Assert.assertFalse(institution.toString()==otherInstitution.toString());
	}
		
}
