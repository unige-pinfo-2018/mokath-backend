package ch.mokath.uniknowledgerestapi.business.service;

import org.junit.Assert;
import org.junit.Test;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.mokath.uniknowledgerestapi.utils.DBHelper;

/**
* @author ornella
* @author zue
*/
@Stateless
public class InstitutionsServiceImplTest {
	@PersistenceContext
	private EntityManager em;
	private DBHelper DBHelper = new DBHelper();

    @Test
    public void ATest(){
//        Assert.assertEquals(isContactEmailOrInstitutionNameAlreadyUsed("test.email@truc.com","instName"),false);
    }

}
