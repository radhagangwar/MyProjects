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

import com.toyotaBharat.core.constants.AcmeConstants;
import com.toyotaBharat.core.models.BasicCarDetailsModel;
import com.toyotaBharat.core.service.BasicCarDetails;

@Component
@Service
public class BasicCarDetailsImpl implements BasicCarDetails {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Reference
	private ResourceResolverFactory resourceFactory;

	@Override
	public List<BasicCarDetailsModel> getBasicCarDetails(String carModelName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE,AcmeConstants.READ_SERVICE);
		ResourceResolver rr = null;
		Node node;
		List<BasicCarDetailsModel> nodemap = new ArrayList<BasicCarDetailsModel>();
		try {

			rr = resourceFactory.getServiceResourceResolver(paramMap);
			Session session = rr.adaptTo(Session.class);
			String SQL = "SELECT * FROM [sling:Folder] AS s WHERE ISCHILDNODE(s,'/etc/toyota/models/" + carModelName+ "')";
			QueryManager qm = session.getWorkspace().getQueryManager();
			Query query = qm.createQuery(SQL, Query.JCR_SQL2);
			QueryResult result = query.execute();
			NodeIterator nodes = result.getNodes();

			while (nodes.hasNext()) {
				node = (Node) nodes.next();
				String SQL1 = "SELECT * FROM [nt:unstructured] AS s WHERE ISCHILDNODE(s,'/etc/toyota/models/"+ carModelName + "/" + node.getName() + "')";
				qm = session.getWorkspace().getQueryManager();
				query = qm.createQuery(SQL1, Query.JCR_SQL2);
				result = query.execute();
				NodeIterator nodeCarDetails = result.getNodes();
				Node innerNode;
				BasicCarDetailsModel carDetails = null;
				while (nodeCarDetails.hasNext()) {
					innerNode = (Node) nodeCarDetails.next();
					carDetails = new BasicCarDetailsModel();
					Resource res = rr.getResource(innerNode.getPath());
					ValueMap prop = res.adaptTo(ValueMap.class);
					carDetails.setCarName(prop.get(AcmeConstants.GRADE, String.class));
					carDetails.setEngineType(prop.get(AcmeConstants.TRANSMISSION, String.class));
					carDetails.setPrice(prop.get(AcmeConstants.CAR_PRICE, String.class));
					carDetails.setDisplacement(prop.get(AcmeConstants.DISPLACEMENT, String.class));
					carDetails.setMaxtorque(prop.get(AcmeConstants.MAX_TORQUE, String.class));

					nodemap.add(carDetails);
					log.info("data in nodemap:::" + nodemap);
					break;
				}
			}
			session.logout();
			rr.close();
		} catch (Exception e) {
			log.error("An error occurred in getBasicCarDetails(): " + e.getMessage());
		}
		return nodemap;
	}

}
