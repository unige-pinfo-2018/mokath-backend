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
	public Institution getInstitution(@NotNull final String id) throws CustomException {
		Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
		wherePredicatesMap.put("id", id);
		Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePredicatesMap,Institution.class,em);

		if (wrappedInst.isPresent()) {
			Institution i = wrappedInst.get();
            return i;
		} else {
			throw new CustomException("Institution not found !");
		}
	}
	
	@Override
	public String getInstitutions() throws CustomException {
//	public List<Institution> getInstitutions() throws CustomException {
		Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
        List<Institution> institutions = em.createQuery("select i from Institution i",Institution.class).getResultList();
//        List<Institution> institutions = DBHelper.getEntitiesFromFields(wherePredicatesMap,Institution.class,em);
		String koL=Arrays.asList(institutions).toString();
        return koL;
//        throw new CustomException("No Institution found !");
	}
	
	@Override
	public Institution updateInstitution(@NotNull Institution i,@NotNull final String id) throws CustomException {
		Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
		wherePredicatesMap.put("id", id);
		Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePredicatesMap,Institution.class,em);

		if (wrappedInst.isPresent()) {
			Institution unwrappedInst = wrappedInst.get();
            i.setId(unwrappedInst.getId());
            return (Institution) em.merge(i);
        }else{
            throw new CustomException("Institution not found !");
        }
	}
	
	/** add/get-all/remove User to/from an Institution */
	@Override
	public String addUser(@NotNull final String uid,@NotNull final String iid) throws CustomException {
		Map<String, Object> wherePMinst = new HashMap<String, Object>();
		wherePMinst.put("id", iid);
		Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePMinst,Institution.class,em);

		if (wrappedInst.isPresent()) {
            Map<String, Object> wherePMuser = new HashMap<String, Object>();
            wherePMuser.put("id", uid);
            Optional<User> wrappedUser = DBHelper.getEntityFromFields(wherePMuser,User.class,em);

            if (wrappedUser.isPresent()) {
                Institution inst = wrappedInst.get();
                User user = em.merge(wrappedUser.get());
                user.setInstitution(inst);
                return user.toString();
            }else{
                throw new CustomException("User not found !");
            }
        }else{
            throw new CustomException("Institution not found !");
        }
	}
	
	@Override
	public String getUsers(@NotNull final String iid) throws CustomException {
		Map<String, Object> wherePMinst = new HashMap<String, Object>();
		wherePMinst.put("id", iid);
		Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePMinst,Institution.class,em);

		if (wrappedInst.isPresent()) {
			Institution inst = wrappedInst.get();
            Map<String, Object> wherePMuser = new HashMap<String, Object>();
            wherePMuser.put("institution",inst.getId());
            List<User> users = DBHelper.getEntitiesFromFields(wherePMuser,User.class,em);
            String koL=Arrays.asList(users).toString();
            return  koL.substring(1,koL.length()-1); //remove the starting [ and ending ] added by arraylist
		} else {
			throw new CustomException("Institution not found !");
        }
	}
	
	@Override
	public void removeUser(@NotNull final String uid,@NotNull final String iid) throws CustomException {
		Map<String, Object> wherePMuser = new HashMap<String, Object>();
		wherePMuser.put("id", uid);
		Optional<User> wrappedUser = DBHelper.getEntityFromFields(wherePMuser,User.class,em);

		if (wrappedUser.isPresent()) {
			User user = em.merge(wrappedUser.get());
			try{
				if(user.getInstitution().getId() == Long.parseLong(iid)){
                    user.removeInstitution();
				}else{
                    throw new CustomException("User does not belong to provided institution !");
				}
			}catch (NullPointerException e) {
                throw new CustomException("User does not belong to any institution !");
            }
		}else{
            throw new CustomException("User not found !");
        }            
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
