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

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import com.toyotaBharat.core.models.FixedHeaderModel;
import com.toyotaBharat.core.service.FixedHeader;

@Component
@Service
public class FixedHeaderImpl implements FixedHeader {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private ResourceResolverFactory resourceFactory;

	@Override
	public Map<String, FixedHeaderModel> fixedHeaderPages() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, "readService");
		Map<String, FixedHeaderModel> fixedpages = new HashMap<String, FixedHeaderModel>();
		ResourceResolver rr = null;

		try {
			Node node;
			log.info("Before creation of resource resolver In getRecursivePages function");
			rr = resourceFactory.getServiceResourceResolver(paramMap);
			log.info("After creation of resource resolver In getRecursivePages function" + rr);
			Session session = rr.adaptTo(Session.class);
			String SQL = "SELECT * FROM [cq:Page] AS s WHERE ISCHILDNODE(s,'/content/toyota-bharat/en/fixed-header-pages')";
			QueryManager qm = session.getWorkspace().getQueryManager();
			Query query = qm.createQuery(SQL, Query.JCR_SQL2);
			QueryResult result = query.execute();
			NodeIterator nodes = result.getNodes();
			while (nodes.hasNext()) {
				node = (Node) nodes.next();
				FixedHeaderModel sm = new FixedHeaderModel();
				Resource res = rr.getResource(node.getPath() + "/jcr:content");
				ValueMap prop = res.adaptTo(ValueMap.class);
				String pageTitle = prop.get("jcr:title", String.class);
				sm.setPageName(node.getName());
				sm.setPagePath(node.getPath());
				fixedpages.put(pageTitle, sm);
			}
			return fixedpages;

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return fixedpages;
	}

	@Override
	public Page getHeaderPages() {
		log.info("--- Inside recursive function getHeaderPages()");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, "readService");
		Page pages = null;
		ResourceResolver resourceResolver = null;
		try {
			resourceResolver = resourceFactory.getServiceResourceResolver(paramMap);
			log.info("--- Inside recursive function getHeaderPages(), created resource resolver : " + resourceResolver);
			PageManager pm = resourceResolver.adaptTo(PageManager.class);
			log.info("--- Inside recursive function getHeaderPages(), Page manager object: " + pm);
			Page navRootPage = pm.getPage("/content/toyota-bharat/en/header-pages");
			log.info("--- Inside recursive function getHeaderPages(), navRootPage object: " + navRootPage);
			pages = navRootPage;
			return pages;

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return pages;
	}
}
