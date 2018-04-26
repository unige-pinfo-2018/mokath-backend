/**
 * 
 */
package ch.mokath.uniknowledgerestapi.dom;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.mail.Address;
import javax.xml.registry.infomodel.User;

import org.junit.Assert;
import org.junit.Test;
/**
 * @author tv0g
 *
 */
public class InstitutionTest {
	
	@Test
	public void institutionBuilderShouldReturnInstitution() {
	
		Institution institution = new Institution.Builder()
		        .with($ -> {
		            $.name = "InsName";
		            $.logoPictureURL = "InsLogo";
		            $.contactEmail = "contact@institution.com";
		        })
		        .build();
		
		System.out.println(institution);
		Assert.assertEquals(institution.getName(),"InsName");
		Assert.assertNull(institution.getAdministrators());
	}
}
