package com.toyotaBharat.core.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import com.toyotaBharat.core.models.Cars;
import com.toyotaBharat.core.service.CarModel;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Service
public class CarModelImpl implements CarModel {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private ResourceResolverFactory resourceFactory;

	@Override
	public Map<Cars, Cars> getCarModelData() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, "readService");
		log.info("Fetching car model data in getCarModelData()");
		ResourceResolver rr = null;
		Node node;
		Map<Cars, Cars> nodemap = new HashMap<Cars, Cars>();
		try {
			log.debug("--- Inside try block ---");
			rr = resourceFactory.getServiceResourceResolver(paramMap);
			log.info("--- Fetched Resource Resolver: " + rr);
			Session session = rr.adaptTo(Session.class);
			String SQL = "SELECT * FROM [sling:Folder] AS s WHERE ISCHILDNODE(s,'/etc/toyota/models')";
			QueryManager qm = session.getWorkspace().getQueryManager();
			Query query = qm.createQuery(SQL, Query.JCR_SQL2);
			QueryResult result = query.execute();
			NodeIterator nodes = result.getNodes();
			Cars cars = null;
			while (nodes.hasNext()) {
				node = (Node) nodes.next();
				cars = new Cars();
				Resource res = rr.getResource(node.getPath());
				ValueMap prop = res.adaptTo(ValueMap.class);
				log.debug("--- Car model properties: " + prop);
				cars.setCarName(node.getName());
				cars.setCarType(prop.get("type", String.class));
				cars.setCarImagePath(prop.get("fileReference", String.class));
				nodemap.put(cars, cars);
			}
			session.logout();
			rr.close();
			log.info("--- Returning nodemap from getCarModelData(): " + nodemap);
			return nodemap;

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return nodemap;

	}

}
