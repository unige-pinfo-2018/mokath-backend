/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.mokath.uniknowledgerestapi.dom.Institution;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.DBHelper;
import ch.mokath.uniknowledgerestapi.utils.CustomException;

/**
 * @author tv0g
 * @author zue
 */
@Stateless
public class InstitutionsServiceImpl implements InstitutionsService {
	
	@PersistenceContext
	private EntityManager em;
	private DBHelper DBHelper = new DBHelper();
	
	@Override
	public void createInstitution(@NotNull Institution i) throws CustomException {
		if(isContactEmailOrInstitutionNameAlreadyUsed(i.getContactEmail(), i.getInstitutionName())) {
			throw new CustomException("Institution name or contact email is already used");
		} else {
            em.persist(i);
        }
	}

	@Override
	public void deleteInstitution(@NotNull Institution i) throws CustomException {
        Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
        wherePredicatesMap.put("id", i.getId());
        Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePredicatesMap,Institution.class,em);
        
        if (wrappedInst.isPresent()) {
            Institution inst= em.merge(wrappedInst.get());

            Map<String, Object> wherePMuser = new HashMap<String, Object>();
            wherePMuser.put("institution",inst.getId());
            List<User> users = DBHelper.getEntitiesFromFields(wherePMuser,User.class,em);
            for(User user : users){ //Remove all users from institution
                user.removeInstitution();
            }
            em.remove(em.contains(i) ? i : em.merge(i));
        } else {
            throw new CustomException("Institution not found !");
        }
	}

	@Override
	public Institution updateInstitution(@NotNull Institution i) throws CustomException {
        return (Institution) em.merge(i);
	}
	
	/** add/remove User to/from an Institution */
	@Override
	public void addUser(User u,Institution i){
        User user = em.merge(u);
        Institution inst = em.merge(i);
        user.setInstitution(inst);
	}
	
	@Override
	public boolean removeUser(User u,Institution i){
        User user = em.merge(u);
        Map<String, Object> wherePM = new HashMap<String, Object>();
		wherePM.put("institution", i.getId());
		wherePM.put("id", u.getId());
		Optional<User> wrappedUser = DBHelper.getEntityFromFields(wherePM, User.class, em);
		if(wrappedUser.isPresent()){
            user.removeInstitution();
            return true;
        }else return false;
	}

	@Override
	public String getUsers(Institution i){
        Institution inst = em.merge(i);
        List<User> users = em.createQuery("select u from User u where u.institution.id = :instId",User.class).setParameter("instId",i.getId()).getResultList();
		String koL=Arrays.asList(users).toString();
		return  koL.substring(1,koL.length()-1); //remove the starting [ and ending ] added by arraylist
	}
	
	/** We do not want 2 institutions with the same name or contact email
	*/
	private boolean isContactEmailOrInstitutionNameAlreadyUsed(String contactEmail, String institutionName) {
		// Check if email is already used
		Map<String, Object> wherePredicatesMapForContactEmail = new HashMap<String, Object>();
		wherePredicatesMapForContactEmail.put("contactEmail", contactEmail);
		
		// Check if institutionName is already used
		Map<String, Object> wherePredicatesMapForInstitutionName = new HashMap<String, Object>();
		wherePredicatesMapForInstitutionName.put("institutionName", institutionName);
		
		Optional<Institution> wrappedInstForEmail = DBHelper.getEntityFromFields(wherePredicatesMapForContactEmail, Institution.class, em);
		Optional<Institution> wrappedInstForName = DBHelper.getEntityFromFields(wherePredicatesMapForInstitutionName, Institution.class, em);

		// If institution already exists with same contact email
		return wrappedInstForEmail.isPresent() || wrappedInstForName.isPresent();
	}
}
