package com.toyotaBharat.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.toyotaBharat.core.constants.AcmeConstants;
import com.toyotaBharat.core.service.AcmeUtil;

@SuppressWarnings("serial")
@SlingServlet(paths = "/bin/setDealerInfo", methods = "POST")
public class InsertDealerInformation extends SlingAllMethodsServlet {

	private Boolean insertFlag = false;
	private String dealerPath = AcmeConstants.ETC_PATH + "/dealers";

	private ResourceResolver resourceResolver;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private ResourceResolverFactory resourceFactory;

	@Reference
	private AcmeUtil acmeUtil;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, AcmeConstants.READ_SERVICE);

		try {
			resourceResolver = resourceFactory.getServiceResourceResolver(paramMap);
			Session session = resourceResolver.adaptTo(Session.class);

			Node root = session.getRootNode();

			if (root.hasNode(acmeUtil.getRelPath(AcmeConstants.ETC_PATH))) {
				if (root.hasNode(acmeUtil.getRelPath(dealerPath))) {
					log.info("The node dealer is present? " + root.hasNode(acmeUtil.getRelPath(dealerPath)));

					Node dealerFolder = root.getNode(acmeUtil.getRelPath(dealerPath));
					this.addDealerNode(request, dealerFolder);
				} else {
					log.info("The node dealer is present? " + root.hasNode(acmeUtil.getRelPath(dealerPath)));

					Node projectNode = root.getNode(acmeUtil.getRelPath(AcmeConstants.ETC_PATH));
					Node dealerFolder = projectNode.addNode("dealers", "sling:Folder");
					this.addDealerNode(request, dealerFolder);
				}
			} else {
				log.info("There is no /etc/toyota path in the JCR. Making it for adding dealer data");
				Node etcPath = root.getNode("etc");
				Node projectNode = etcPath.addNode(acmeUtil.getNodeName(AcmeConstants.ETC_PATH), "sling:Folder");
				Node dealerFolder = projectNode.addNode("dealers", "sling:Folder");
				this.addDealerNode(request, dealerFolder);
			}
			session.save();
			session.logout();

		} catch (LoginException | RepositoryException e) {
			log.error("A LoginException or RepositoryException occured in InsertDealerInformation doPost: "
					+ e.getMessage());
		} catch (Exception ex) {
			log.error("A Error occured in InsertDealerInformation doPost: " + ex.getMessage());
		} finally {
			response.getWriter().write(this.insertFlag.toString());
		}
	}

	private void addDealerNode(SlingHttpServletRequest req, Node dealerNode) {

		log.info("the dealer node is: " + dealerNode);
		String stateTagID = null;
		String cityTagID = null;
		String facilityTagID = null;

		String state = req.getParameter("state");
		String city = req.getParameter("city");
		String dealerName = req.getParameter("dealerName");
		String dealerArea = req.getParameter("dealerArea");
		String dealerStreetAdd = req.getParameter("dealerStreetAdd");
		String pincode = req.getParameter("pincode");
		String facility = req.getParameter("facility");
		String website = req.getParameter("website");
		String phone = req.getParameter("phone");
		log.info("The phone num frm request param is::::::::::::::::" + req.getParameter("phone"));
		log.info("The phone num frm string phone in java is::::::::::::::::" + phone);
		String fax = req.getParameter("fax");
		String service = req.getParameter("service");
		String helpline = req.getParameter("helpline");

		if (!(state.isEmpty())
				&& !(city.isEmpty())
				&& !(dealerName.isEmpty())
				&& !(dealerArea.isEmpty())
				&& !(dealerStreetAdd.isEmpty())
				&& !(pincode.isEmpty())
				&& !(phone.isEmpty())
				&& !(facility.isEmpty())) {

			TagManager tagManager = resourceResolver.adaptTo(TagManager.class);

			Tag[] stateTag = tagManager.findByTitle(state).tags;
			for (Tag tag : stateTag) {
				stateTagID = tag.getTagID();
			}

			Tag[] cityTag = tagManager.findByTitle(city).tags;
			for (Tag tag : cityTag) {
				cityTagID = tag.getTagID();
			}

			Tag[] facilityTag = tagManager.findByTitle(facility).tags;
			for (Tag tag : facilityTag) {
				facilityTagID = tag.getTagID();
			}

			String[] tagsArr = { stateTagID, cityTagID, facilityTagID };

			String dealerNodeName = (dealerName.toLowerCase() + "-" + dealerArea.toLowerCase() + "-" + facility)
					.replace(" ", "-");

			try {
				Node infoNode = dealerNode.addNode(dealerNodeName, "sling:OrderedFolder");
				Node dealerInfoNode = infoNode.addNode("info", "nt:unstructured");

				dealerInfoNode.addMixin("cq:Taggable");
				dealerInfoNode.setProperty("jcr:title", dealerName);
				dealerInfoNode.setProperty("street", dealerStreetAdd);
				dealerInfoNode.setProperty("area", dealerArea);
				dealerInfoNode.setProperty("city", city);
				dealerInfoNode.setProperty("state", state);
				dealerInfoNode.setProperty("pincode", pincode);
				dealerInfoNode.setProperty("phone", phone);
				dealerInfoNode.setProperty("cq:tags", tagsArr);
				if (!(website.isEmpty()))
					dealerInfoNode.setProperty("website", website);
				if (!(fax.isEmpty()))
					dealerInfoNode.setProperty("fax", fax);
				if (!(service.isEmpty()))
					dealerInfoNode.setProperty("service", service);
				if (!(helpline.isEmpty()))
					dealerInfoNode.setProperty("helpline", helpline);

				this.insertFlag = true;

				log.info("Node Added: " + dealerInfoNode);
			} catch (RepositoryException e) {
				log.error("A RepositoryException occured in InsertDealerInformation addDealerNode: " + e.getMessage());
			} catch (Exception ex) {
				log.error("A Error occured in InsertDealerInformation addDealerNode: " + ex.getMessage());
			}
		}
	}
}