package ch.mokath.uniknowledgerestapi.business.service;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @author matteo113
 */
@Stateless
public class SearchServiceImpl implements SearchService {
	
	@PersistenceContext
	private EntityManager em;
	private Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);
	
	@Override
	public String esSearch(String q) {
		RestHighLevelClient client = new RestHighLevelClient(
		        RestClient.builder(
		                new HttpHost("129.194.69.24", 9201, "http")));
		
		SearchRequest searchRequest = new SearchRequest("questions", "answers");
		searchRequest.types("doc");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		
		MultiMatchQueryBuilder queryBuilder = new MultiMatchQueryBuilder(q, "title", "text");
		queryBuilder.fuzziness(Fuzziness.AUTO);
		
		searchSourceBuilder.query(queryBuilder);
		searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
		//searchSourceBuilder.sort(new FieldSortBuilder("popularity").order(SortOrder.DESC));
		
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse = new SearchResponse();
		try {
			searchResponse = client.search(searchRequest);
			client.close();
		} catch (IOException e) {
			log.error("Exception thrown in SearchServiceImpl : " + e.getMessage());
		}
		
		
		return searchResponse.toString();
	}

}
