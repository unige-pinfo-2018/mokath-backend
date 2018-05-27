/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

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
	public void createInstitution(Institution i) throws CustomException {
        try{
            if(isContactEmailOrInstitutionNameAlreadyUsed(i.getContactEmail(), i.getInstitutionName())) {
                throw new CustomException("institution name or contact email already in use");
            } else {
                em.persist(i);
            }
        } catch (NullPointerException ne) {
            throw new CustomException("empty institution");
        } catch (ConstraintViolationException e) {
            throw new CustomException("invalid input");
        }
	}

	@Override
	public Institution getInstitution(final String id) throws CustomException {
        try{
            Long iid = Long.valueOf(id);
            Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
            wherePredicatesMap.put("id",iid);
            Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePredicatesMap,Institution.class,em);

            if (wrappedInst.isPresent()) {
                return wrappedInst.get();
            } else {
                throw new CustomException("institution not found");
            }
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong institution ID");
        }
	}

	@Override
	public List<Institution> getInstitutions() throws CustomException {
        List<Institution> institutions = em.createQuery("select i from Institution i",Institution.class).getResultList();
        return institutions;
	}
	
	@Override
	public Institution updateInstitution(Institution i,final String id) throws CustomException {
        try{
            Long iid = Long.valueOf(id);
            if(isContactEmailOrInstitutionNameAlreadyUsed(i.getContactEmail(),i.getInstitutionName(),id)) {
                throw new CustomException("institution name or contact email already in use");
            }else{
                Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
                wherePredicatesMap.put("id",iid);
                Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePredicatesMap,Institution.class,em);

                if (wrappedInst.isPresent()) {
                    Institution inst = wrappedInst.get();
                    i.setId(inst.getId());
                    Institution returnValue = (Institution) em.merge(i);
                    em.flush();
                    return returnValue;
                }else{
                    throw new CustomException("institution not found");
                }
            }
        } catch (NullPointerException ne) {
            throw new CustomException("empty institution");
        }catch (ConstraintViolationException e){
            throw new CustomException("invalid input");
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong institution ID");
        }
	}

	@Override
	public void deleteInstitution(final String id) throws CustomException {
         try{
            Long iid = Long.valueOf(id);
            Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
            wherePredicatesMap.put("id", id);
            Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePredicatesMap,Institution.class,em);

            if (wrappedInst.isPresent()) {
                Institution inst= em.merge(wrappedInst.get());
                for(User u : inst.getUsers()){ //Remove all users from institution first
                    User user = em.merge(u);
                    user.removeInstitution();
                }
                em.remove(inst);
            } else {
                throw new CustomException("institution not found");
            }
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong institution ID");
        }
	}

	/** add/get-all/remove User to/from an Institution */
	@Override
	public User addUser(final String uid,final String iid) throws CustomException {
        try{
            Long uidl = Long.valueOf(uid);
            Map<String, Object> wherePMuser = new HashMap<String, Object>();
            wherePMuser.put("id",uidl);
            Optional<User> wrappedUser = DBHelper.getEntityFromFields(wherePMuser,User.class,em);

            if (wrappedUser.isPresent()) {
                Long iidl = Long.valueOf(iid);
                Map<String, Object> wherePMinst = new HashMap<String, Object>();
                wherePMinst.put("id",iidl);
                Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePMinst,Institution.class,em);

                if (wrappedInst.isPresent()) {
                    Institution inst = wrappedInst.get();
                    User user = em.merge(wrappedUser.get());
                    user.setInstitution(inst);
                    return user;
                }else{
                    throw new CustomException("institution not found");
                }
            }else{
                throw new CustomException("user not found");
            }
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong institution or user ID");
        }
	}
	
	@Override
	public List<User> getUsers(final String iid) throws CustomException {
        try{
            Long iidl = Long.valueOf(iid);
            Map<String, Object> wherePMinst = new HashMap<String, Object>();
            wherePMinst.put("id",iidl);
            Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePMinst,Institution.class,em);

            if (wrappedInst.isPresent()) {
                Institution inst = em.merge(wrappedInst.get());
                Map<String, Object> wherePMuser = new HashMap<String, Object>();
                wherePMuser.put("institution",inst.getId());
                List<User> users = DBHelper.getEntitiesFromFields(wherePMuser,User.class,em);
                return users;
                }else{
                    throw new CustomException("institution not found");
            }
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong institution ID");
        }
	}
	
	@Override
	public void removeUser(final String uid,final String iid) throws CustomException {
        try{
            Long uidl = Long.valueOf(uid);
            Map<String, Object> wherePMuser = new HashMap<String, Object>();
            wherePMuser.put("id",uidl);
            Optional<User> wrappedUser = DBHelper.getEntityFromFields(wherePMuser,User.class,em);

            if (wrappedUser.isPresent()) {
                User user = em.merge(wrappedUser.get());
                if(user.getInstitution().getId() == Long.parseLong(iid)){
                    user.removeInstitution();
                }else{
                    throw new CustomException("user does not belong to provided institution");
                }
            }else{
                throw new CustomException("user not found");
            }            
        }catch(NullPointerException e){
            throw new CustomException("user does not belong to any institution");
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong institution or user ID");
        }
	}

	/** We do not want 2 Institutions with the same name or contact email
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
	
	/** We do not want 2 Institutions with the same name or contact email
	* But omit to check on Institution with id (needed for update)
	*/
	private boolean isContactEmailOrInstitutionNameAlreadyUsed(String contactEmail, String institutionName,String id) {
		// Check if email is already used
		Map<String, Object> wherePredicatesMapForContactEmail = new HashMap<String, Object>();
		wherePredicatesMapForContactEmail.put("contactEmail", contactEmail);
		
		// Check if institutionName is already used
		Map<String, Object> wherePredicatesMapForInstitutionName = new HashMap<String, Object>();
		wherePredicatesMapForInstitutionName.put("institutionName", institutionName);
		
		Map<String, Object> whereNotPMid = new HashMap<String, Object>();
		whereNotPMid.put("id", id);

		Optional<Institution> wrappedInstForEmail = DBHelper.getEntityFromFields(wherePredicatesMapForContactEmail,whereNotPMid,Institution.class, em);
		Optional<Institution> wrappedInstForName = DBHelper.getEntityFromFields(wherePredicatesMapForInstitutionName,whereNotPMid,Institution.class, em);

		// If institution already exists with same contact email
		return wrappedInstForEmail.isPresent() || wrappedInstForName.isPresent();
	}
}
