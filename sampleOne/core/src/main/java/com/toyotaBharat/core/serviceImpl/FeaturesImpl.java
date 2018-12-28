package com.toyotaBharat.core.serviceImpl;

import java.io.File;
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

import com.toyotaBharat.core.service.Features;
import com.toyotaBharat.core.util.AcmeUtils;
import com.toyotaBharat.core.constants.AcmeConstants;
import com.toyotaBharat.core.models.ImageModel;

@Component
@Service
public class FeaturesImpl implements Features {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private ResourceResolverFactory resourceFactory;

	@Override
	public List<ImageModel> populateDamImages(String carModelName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, AcmeConstants.READ_SERVICE);
		ImageModel imageModel;
		List<ImageModel> imageUrlDescriptionList = new ArrayList<>();
		try {
			ResourceResolver resourceResolver = resourceFactory.getServiceResourceResolver(paramMap);
			Session session = resourceResolver.adaptTo(Session.class);

			String SQL = "SELECT * FROM [dam:Asset] AS s WHERE ISDESCENDANTNODE(s,'/content/dam/toyota-bharat/models/"
					+ carModelName + "/features')";
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
				File file = new File(imagePath);
				AcmeUtils util = new AcmeUtils();
				String parentPath = file.getParent().replace("\\", "/");
				String parentName = util.getCarModelName(parentPath); // get parent name from parent path
				imageModel.setParentName(parentName);
				// capitalize first letter of parentName as parentTitle
				String parentTitle = parentName.substring(0, 1).toUpperCase() + parentName.substring(1);
				imageModel.setParentTitle(parentTitle);
				Resource currentPageResource = resourceResolver.getResource(imagePath + "/jcr:content/metadata");
				ValueMap prop = currentPageResource.adaptTo(ValueMap.class);
				String imageTitle = prop.get("dc:title", (String) null);
				imageModel.setImageTitle(imageTitle);
				log.info(" imageModel ::::::::: title " + imageModel.getImageTitle() + " \n imagePath "
						+ imageModel.getImagePath() + " \n parentName" + imageModel.getParentName());
				imageUrlDescriptionList.add(imageModel);
			}
		} catch (Exception e) {
			log.error("An Excepetion Occured" + e.getMessage());
		}
		return imageUrlDescriptionList;
	}
}
