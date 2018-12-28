package com.toyotaBharat.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.toyotaBharat.core.constants.AcmeConstants;

@SuppressWarnings("serial")
@SlingServlet(paths = "/bin/fetchStatesCities", methods = "GET")
public class FetchStatesCities extends SlingSafeMethodsServlet {

	private String statesTagPath = AcmeConstants.TAG_PATH + "/states";

	private ResourceResolver resourceResolver;
	private QueryManager queryManager;
	private Session session;
	private Node node;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private ResourceResolverFactory resourceFactory;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("In the doGet method of FetchStatesCities");
		Map<String, List<String>> jsonMap = this.dealerLocations();
		
		Gson gson = new Gson();
		String json = gson.toJson(jsonMap);
		
		log.info("The fetched States and Cities in the json format: " + json);
		response.getWriter().write(json);
	}

	protected Map<String, List<String>> dealerLocations() {
		String parentName;
		String nodeName;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, AcmeConstants.READ_SERVICE);

		Map<String, List<String>> locationMap = new HashMap<>();
		String stateQuery = "SELECT * FROM [cq:Tag] AS s WHERE ISCHILDNODE(s,'" + statesTagPath + "')";
		
		log.info("In the dealerLocations method where we get states");

		try {
			resourceResolver = resourceFactory.getServiceResourceResolver(paramMap);
			session = resourceResolver.adaptTo(Session.class);

			queryManager = session.getWorkspace().getQueryManager();
			Query query = queryManager.createQuery(stateQuery, Query.JCR_SQL2);
			QueryResult stateResult = query.execute();

			NodeIterator nodes = stateResult.getNodes();

			while (nodes.hasNext()) {

				node = (Node) nodes.next();
				nodeName = node.getName();

				Resource res = resourceResolver.getResource(node.getPath());
				ValueMap prop = res.adaptTo(ValueMap.class);
				parentName = prop.get("jcr:title", String.class);

				List<String> cityList = this.citiesList(nodeName);

				locationMap.put(parentName, cityList);
			}
			session.logout();
			resourceResolver.close();
		} catch (RepositoryException | LoginException e) {
			log.error("An Error Occured: " + e.getMessage());
		} catch (Exception ex){
			log.error("An Error Occured: " + ex.getMessage());
		}
		return locationMap;
	}

	protected List<String> citiesList(String parentNode) {
		String cityName;
		List<String> cityList = new ArrayList<>();
		String cityPath = statesTagPath + "/" + parentNode;
		String citySQL = "SELECT * FROM [cq:Tag] AS s WHERE ISCHILDNODE(s,'" + cityPath + "')";
		
		log.debug("In the citiesList method where we get cities of the corresponding state: " + parentNode);

		try {
			Query query = queryManager.createQuery(citySQL, Query.JCR_SQL2);
			QueryResult cityResult = query.execute();

			NodeIterator cityNodes = cityResult.getNodes();

			while (cityNodes.hasNext()) {
				node = (Node) cityNodes.next();
				Resource res = resourceResolver.getResource(node.getPath());
				ValueMap prop = res.adaptTo(ValueMap.class);
				cityName = prop.get("jcr:title", String.class);
				cityList.add(cityName);
			}
		} catch (RepositoryException e) {
			log.error("Catch block of citiesList" + e.getMessage());
		} catch (Exception ex){
			log.error("An Error Occured: " + ex.getMessage());
		}
		return cityList;
	}
}