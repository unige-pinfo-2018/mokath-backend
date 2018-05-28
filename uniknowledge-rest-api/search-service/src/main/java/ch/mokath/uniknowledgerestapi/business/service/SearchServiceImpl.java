/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/*
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.mokath.uniknowledgerestapi.dom.Institution;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.DBHelper;
import ch.mokath.uniknowledgerestapi.utils.CustomException;
*/

/**
 * @author zue
 */
@Stateless
public class SearchServiceImpl implements SearchService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void allSearch() {
	}

}
