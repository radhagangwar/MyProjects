package com.toyotaBharat.core.serviceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.toyotaBharat.core.service.Gallery;

@Component
@Service
public class GalleryImpl implements Gallery {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private ResourceResolverFactory resourceFactory;

	@Override
	public List<String> getImage(String path) throws SQLException, org.apache.sling.api.resource.LoginException {
		List<String> galleryImages = new ArrayList<String>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, "readService");
		ResourceResolver rr;
		rr = resourceFactory.getServiceResourceResolver(paramMap);
		Session session = rr.adaptTo(Session.class);
		String SQL = "SELECT * FROM [dam:Asset] AS s WHERE ISCHILDNODE([" + path + "]) ";
		NodeIterator nodes;
		try {
			QueryManager qm = session.getWorkspace().getQueryManager();
			Query query = qm.createQuery(SQL, Query.JCR_SQL2);
			QueryResult result = query.execute();
			nodes = result.getNodes();
			while (nodes.hasNext()) {
				Node node1 = nodes.nextNode();
				String name = node1.getPath();
				galleryImages.add(name);
			}
		} catch (RepositoryException e) {

			log.error("An error occurred: " + e.getMessage());
		}

		return galleryImages;
	}

}
