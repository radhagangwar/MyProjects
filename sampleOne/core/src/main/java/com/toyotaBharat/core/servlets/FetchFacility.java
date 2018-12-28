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
@SlingServlet(paths = "/bin/getFacility", methods = "GET")
public class FetchFacility extends SlingSafeMethodsServlet {

	private String faclitiesTagPath = AcmeConstants.TAG_PATH + "/facility";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private ResourceResolverFactory resourceFactory;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		String nodeName;
		String nodeTitle;

		List<String> facilitiesList = new ArrayList<>();
		Map<String, String> facilitiesMap = new HashMap<>();

		log.debug("In the doGet method of FetchFacility");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, AcmeConstants.READ_SERVICE);

		String facilityQuery = "SELECT * FROM [cq:Tag] AS s WHERE ISCHILDNODE(s,'" + faclitiesTagPath + "')";

		try {

			ResourceResolver resourceResolver = resourceFactory.getServiceResourceResolver(paramMap);
			Session session = resourceResolver.adaptTo(Session.class);

			QueryManager queryManager = session.getWorkspace().getQueryManager();
			Query query = queryManager.createQuery(facilityQuery, Query.JCR_SQL2);
			QueryResult facilityResult = query.execute();

			// As per comment on the pull
			// request(ref:http://bitbucket.cybage.com/projects/CYB2961/repos/aem_toyotabharat/pull-requests/30/overview)
			// the fetching of node will change with integration of AcmeUtils
			// and AcmeUtil in the later stages of code.
			NodeIterator nodes = facilityResult.getNodes();

			while (nodes.hasNext()) {
				Node node = (Node) nodes.next();
				nodeName = node.getName();

				Resource res = resourceResolver.getResource(node.getPath());
				ValueMap prop = res.adaptTo(ValueMap.class);
				nodeTitle = prop.get("jcr:title", String.class);

				facilitiesList.add(nodeTitle);
				facilitiesMap.put(nodeName, nodeTitle);
			}

			session.logout();
			resourceResolver.close();

			Gson gson = new Gson();
			String json = gson.toJson(facilitiesMap);
			log.info("The fetched facilities in the json format is: " + json);
			response.getWriter().write(json);

		} catch (RepositoryException | LoginException e) {
			log.error("An Error Occured: " + e.getMessage());
		} catch (Exception ex) {
			log.error("An Error Occured: " + ex.getMessage());
		}

	}

}
