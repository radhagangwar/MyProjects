package com.toyotaBharat.core.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.query.Query;

import com.toyotaBharat.core.service.QueryModelPath;

@Component
@Service
public class QueryImpl implements QueryModelPath {
	 /** Default log. */
   protected final Logger log = LoggerFactory.getLogger(this.getClass());
	 
    @Reference 
    private ResourceResolverFactory resourceFactory;

    Map<String,String> modelMap = new HashMap<String,String>();
    Node node;
    
    /* Method to get all the Models in the variantForm in the drop-down */
	@Override
	public Map<String, String> getModels() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, "readService");

		ResourceResolver rr;
		try {
			rr = resourceFactory.getServiceResourceResolver(paramMap);
			Session session = rr.adaptTo(Session.class);
			String SQL = "SELECT * FROM [sling:Folder] AS s WHERE ISDESCENDANTNODE(s,'/etc/toyota/models') AND (s.description='Model Node')";
			QueryManager qm = session.getWorkspace().getQueryManager();
			Query query = qm.createQuery(SQL, Query.JCR_SQL2);
			QueryResult result = query.execute();
			NodeIterator nodes = result.getNodes();
			while (nodes.hasNext()) {
				node = (Node) nodes.next();
				modelMap.put(node.getName(), "etc/toyota/models/"+node.getName());
			}
			session.logout();
		} catch (Exception e) {
			log.error("Catch block of QueryImpl" + e.getMessage());
		}
		return modelMap;
	}
}
