package ch.mokath.uniknowledgerestapi.business.service;

import javax.ejb.Local;

/**
  * @author zue
  * @author matteo113
 */
@Local
public interface SearchService {

	String esSearch(String query);
	
}
