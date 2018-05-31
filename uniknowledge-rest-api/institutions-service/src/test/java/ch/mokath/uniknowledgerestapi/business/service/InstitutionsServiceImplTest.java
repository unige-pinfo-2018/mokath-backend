package ch.mokath.uniknowledgerestapi.business.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.dom.Institution;
import ch.mokath.uniknowledgerestapi.utils.DBHelper;

import javax.persistence.Query;
import org.junit.*;
/*import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ch.mokath.uniknowledgerestapi.utils.CustomException;
*/
import org.junit.Test;
import java.util.List;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
* @author ornela
* @author zue
*/
@Stateless
public class InstitutionsServiceImplTest {/*extends JPAHibernateTest {
    @Test
    public void testGetObjectById_success() {
        Institution inst = em.find(Institution.class, 1);
        assertNotNull(inst);
    }

/* /    @Mock    EntityManager em;
    
//@InjectMocks //
//    @Inject
    private InstitutionsService institutionsService;
    
    private EntityManager em;
    private Query query;


    private List<Institution> institutions = new ArrayList<Institution>();
    
    private    Institution inst1;
    
    @Before
    public void setUp() {
         em = mock(EntityManager.class);
    institutionsService = mock(InstitutionsServiceImpl.class);
      HashSet<String> domains = new HashSet<String>();
        inst1 = new Institution("InsName", "InsLogo", "contact@institution.com", domains);
        Institution inst2 = new Institution("OtherInsName", "OtherLogo", "contact@other.com", domains);
        this.institutions.add(this.inst1);
        this.institutions.add(inst2);
/*        try{
     institutionsService.createInstitution(inst1);
     }catch (CustomException ce){
     }* /
query = mock(Query.class);
 //private List<Institution> insts = institutionsService.getInstitutions();
 
// institutionsService = mock(InstitutionsService.class);
 MockitoAnnotations.initMocks(this);
    }
    

/*	@PersistenceContext
	private EntityManager em;
	private DBHelper DBHelper = new DBHelper();
* /
    @Test
    public void test() {
        Assert.assertNotNull(institutionsService);
    }
    
    @Test
    public void getInstitutionsTest(){
         try{
        when(institutionsService.getInstitution("1")).thenReturn(inst1);
       System.out.println("**** [TOTO] : "+ institutionsService.getInstitution("1").toString());
     }catch (CustomException ce){
     }
       String queryString = "select i from Institution i";
        when(query.getResultList()).thenReturn(this.institutions);
        when(em.createQuery(queryString)).thenReturn(query);
        when(institutionsService.getInstitutions()).thenReturn(this.institutions);
/* /        when(institutionsService.getInstitutions(),Institution.class).thenReturn(this.institutions);
        List<Institution> insts =  institutionsService.getInstitutions();
        Assert.assertEquals(insts, institutions);
* /
        List<Institution> insts = institutionsService.getInstitutions();
//        verify(query).getResultList();
//verify(em).createQuery(queryString);
        Assert.assertEquals(insts, institutions);
 System.out.println("**** [TOTO] : " +   insts.toString());
       System.out.println("**** [TOTO] : "+ this.institutions.toString());
       System.out.println("**** [TOTO] : "+ institutions.toString());
/*        try{
            List<Institution> institutions = institutionsService.getInstitutions();
            Assert.assertTrue(institutions.toString().equals("[]"));
        }catch (NullPointerException e){
        }
//        Assert.assertEquals(isContactEmailOrInstitutionNameAlreadyUsed("test.email@truc.com","instName"),false);
* /   }
 
    @Test
    public void remove() {
        
        doNothing().when(em).remove(this.inst1);
    }/* */
}
