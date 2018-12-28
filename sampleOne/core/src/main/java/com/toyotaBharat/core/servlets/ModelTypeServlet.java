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
@SlingServlet(paths = "/bin/mySearchServlet", methods = "GET", metatype =true)
public class ModelTypeServlet extends SlingAllMethodsServlet{

	 /** Default log. */
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	   
	@Reference
	private ResourceResolverFactory resourceFactory;

	/*Method to get all the Model and it's Fuel type on the url /bin/mySearchServlet */
	@Override    
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {   

		resp.setContentType("application/json");

		List<String> modelTypeList = new ArrayList<String>();
		Node node;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, "readService");

		ResourceResolver rr;

		try {
			rr = resourceFactory.getServiceResourceResolver(paramMap);
			Session session = rr.adaptTo(Session.class);
			String SQL = "SELECT * FROM [sling:Folder] AS s WHERE ISDESCENDANTNODE(s,'/etc/toyota/models') AND (s.description='Model Type')";
			QueryManager qm = session.getWorkspace().getQueryManager();
			Query query = qm.createQuery(SQL, Query.JCR_SQL2);
			QueryResult result = query.execute();
			NodeIterator nodes = result.getNodes();
			String temp[];
			String tem;
			while (nodes.hasNext()) {
				node = (Node) nodes.next();
				String operationString = node.getPath();
				temp = operationString.split("/");
				tem = operationString.substring(0, operationString.lastIndexOf('/', operationString.lastIndexOf('/')));
				modelTypeList.add(tem + ":" + temp[5]);
			}
			rr.close();
			resp.getWriter().println(modelTypeList);
			session.logout();
		} catch (Exception e) {
			log.error("Catch block of QueryImpl" + e.getMessage());
		}

	 }

}
