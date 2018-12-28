package com.toyotaBharat.core.serviceImpl;


import org.apache.sling.api.resource.Resource;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import org.apache.sling.api.resource.ValueMap;

import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;

import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;


import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import com.toyotaBharat.core.models.NavigationListModel;
import com.toyotaBharat.core.service.NavigationList;

@Component

@Service
public class NavigationListImpl implements NavigationList {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Reference 
    private ResourceResolverFactory resourceFactory;
	
	@Override
	public List<NavigationListModel> getNavList(String carModelName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, "readService");
		NavigationListModel navListModel;
		List<NavigationListModel> navBarList = new ArrayList<>();;	
		
		try{
			ResourceResolver resourceResolver = resourceFactory.getServiceResourceResolver(paramMap);
			Session session = resourceResolver.adaptTo(Session.class);
				
			String SQL = "SELECT * FROM [sling:Folder] AS s WHERE ISCHILDNODE(s,'/content/dam/toyota-bharat/models/" + carModelName + "')";
			log.info("SQL:" + SQL);
			
			QueryManager qm = session.getWorkspace().getQueryManager();
			Query query = qm.createQuery(SQL, Query.JCR_SQL2);
			QueryResult result = query.execute();
			NodeIterator nodes = result.getNodes();						    
			    
			while (nodes.hasNext()) {
				Node node = nodes.nextNode();
				
				navListModel = new NavigationListModel();										
								
				Resource currentPageResource = resourceResolver.getResource(node.getPath()+"/jcr:content");				
				ValueMap prop = currentPageResource.adaptTo(ValueMap.class);	
				
				String navBarTitle = prop.get("jcr:title", String.class);				
				navListModel.setPageTitle(navBarTitle);					
				navListModel.setPageName(node.getName());
				
				log.info("navListModel.getPageName()"+ navListModel.getPageName());				
				log.info("navBarTitle:" + navBarTitle);				
						
				navBarList.add(navListModel);					
			}
			
		} catch (Exception e) {
			log.error("An error occurred:" + e.getMessage());
		}
		return navBarList;	
	}

}
