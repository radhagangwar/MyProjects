package com.toyotaBharat.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("serial")
@SlingServlet(paths = "/bin/findRelatedFuelTypes", methods = "POST")
public class FindRelatedFuelTypes extends SlingAllMethodsServlet{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private ResourceResolverFactory resourceFactory;

	
    
    /*Method to get the related fuel type as per selection of the Model in VariantForm*/
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		List<String> fuelTypeList = new ArrayList<String>();
	    Node node;
	    
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, "readService");

		String selectedModel = request.getParameter("selectedModel");
		log.info("Selected Model-> " + selectedModel);
		ResourceResolver rr;
		try {
			rr = resourceFactory.getServiceResourceResolver(paramMap);
			Session session = rr.adaptTo(Session.class);
			String SQL = "SELECT * FROM [sling:OrderedFolder] AS s WHERE ISDESCENDANTNODE(s,'/" + selectedModel + "')";
			QueryManager qm = session.getWorkspace().getQueryManager();
			Query query = qm.createQuery(SQL, Query.JCR_SQL2);
			QueryResult result = query.execute();
			NodeIterator nodes = result.getNodes();
			while (nodes.hasNext()) {
				node = (Node) nodes.next();
				fuelTypeList.add(node.getName());
			}
			String stringData = fuelTypeList.toString();
			response.getWriter().write(stringData);
			//log.info("jsonData --> " + jsonData);
			session.logout();
		} catch (Exception e) {
			log.error("Catch block of FindRelatedFuelTypes" + e.getMessage());
		}

	}
}
