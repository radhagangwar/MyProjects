package com.toyotaBharat.core.serviceImpl;

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

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toyotaBharat.core.service.TopFeatures;
import com.toyotaBharat.core.models.ImageModel;


@Component
@Service
public class TopFeaturesImpl implements TopFeatures {	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Reference 
    private ResourceResolverFactory resourceFactory;	

	@Override
	public List<ImageModel> populateDamImages(String carModelName) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ResourceResolverFactory.SUBSERVICE, "readService");
			ImageModel imageModel;
			List<ImageModel> imageUrlDescriptionList = new ArrayList<>();
			try{
				ResourceResolver resourceResolver = resourceFactory.getServiceResourceResolver(paramMap);
				Session session = resourceResolver.adaptTo(Session.class);
				
				String SQL = "SELECT * FROM [dam:Asset] AS s WHERE ISCHILDNODE(s,'/content/dam/toyota-bharat/models/" + carModelName + "/top-features')";
				log.info("SQL:::::::::" + SQL);
				Node node;			
				QueryManager qm = session.getWorkspace().getQueryManager();
				Query query = qm.createQuery(SQL, Query.JCR_SQL2);
				QueryResult result = query.execute();
				NodeIterator nodes = result.getNodes();
				
				while (nodes.hasNext()) {
					node = (Node) nodes.next();
					imageModel = new ImageModel();
					String imagePath = node.getPath();
					imageModel.setImagePath(imagePath);
					log.info("imagePath:::::::::" + imagePath);
					Resource currentPageResource = resourceResolver.getResource(imagePath + "/jcr:content/metadata");
					ValueMap prop = currentPageResource.adaptTo(ValueMap.class);
					String imageDescription = prop.get("dc:description", (String) null);
					imageModel.setImageDescription(imageDescription);
					log.info("imageDescription:::::::::" + imageDescription);
					imageUrlDescriptionList.add(imageModel);
				}
			}
			catch (Exception e) {
	            e.printStackTrace();
	            log.error("An Excepetion Occured" + e.getMessage());
	        }
		return imageUrlDescriptionList;
	}
}
