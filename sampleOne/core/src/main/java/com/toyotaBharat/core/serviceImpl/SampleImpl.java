package com.toyotaBharat.core.serviceImpl;

import java.util.HashMap;
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
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toyotaBharat.core.service.SampleModel;

@Component
@Service
public class SampleImpl implements Sample {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private ResourceResolverFactory resourceFactory;

	@Override

	public Map<SampleModel, SampleModel> getData() {
		// creating map to add service name that is set with configuration in
		// OSGI
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// Mention the subServiceName you had used in the User Mapping
		paramMap.put(ResourceResolverFactory.SUBSERVICE, "readService");
		log.info("resource resolver");
		ResourceResolver rr = null;
		Node node;
		Map<SampleModel, SampleModel> nodemap = new HashMap<SampleModel, SampleModel>();
		try {
			log.info("After the  try block of getData function");
			rr = resourceFactory.getServiceResourceResolver(paramMap);
			log.info("After the resource resolver" + rr);
			Session session = rr.adaptTo(Session.class);
			String SQL = "SELECT * FROM [sling:Folder] AS s WHERE ISCHILDNODE(s,'/content/usergenerated/content/forms/af/Test-Multi-Form')";
			QueryManager qm = session.getWorkspace().getQueryManager();
			Query query = qm.createQuery(SQL, Query.JCR_SQL2);
			QueryResult result = query.execute();
			NodeIterator nodes = result.getNodes();
			SampleModel sampleModel = null;
			while (nodes.hasNext()) {
				node = (Node) nodes.next();
				sampleModel = new SampleModel();
				Resource res = rr.getResource(node.getPath());
				ValueMap prop = res.adaptTo(ValueMap.class);
				String carModel = prop.get("carModel", String.class);
				int price = prop.get("price", Integer.class);
				int type = prop.get("type", Integer.class);
				String state = prop.get("states", String.class);
				String variantName = prop.get("variantName", String.class);
				sampleModel.setCarModelName(carModel);
				sampleModel.setCarModelPrice(price);
				sampleModel.setCarModelType(type);
				sampleModel.setState(state);
				sampleModel.setVariantName(variantName);
				nodemap.put(sampleModel, sampleModel);
			}
			session.logout();
			log.info("Before returning getData function");
			return nodemap;

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return nodemap;

	}
}
