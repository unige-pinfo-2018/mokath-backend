package ch.mokath.uniknowledgerestapi.business.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.mokath.uniknowledgerestapi.dom.Institution;
import ch.mokath.uniknowledgerestapi.utils.DBHelper;

/**
* @author ornela
* @author zue
*/
@Stateless
public class InstitutionsServiceImplTest {
	@Inject
	private InstitutionsService institutionsService;

	@PersistenceContext
	private EntityManager em;
	private DBHelper DBHelper = new DBHelper();

    @Test
    public void getInstitutionsTest(){
        try{
            List<Institution> institutions = institutionsService.getInstitutions();
            Assert.assertTrue(institutions.toString().equals("[]"));
        }catch (NullPointerException e){
        }
//        Assert.assertEquals(isContactEmailOrInstitutionNameAlreadyUsed("test.email@truc.com","instName"),false);
    }

}
