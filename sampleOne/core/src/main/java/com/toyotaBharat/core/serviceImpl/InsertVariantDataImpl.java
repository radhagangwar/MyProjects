package com.toyotaBharat.core.serviceImpl;

import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.Node;
import com.toyotaBharat.core.models.CarVariant;
import com.toyotaBharat.core.service.InsertVariantData;

@Component
@Service
public class InsertVariantDataImpl implements InsertVariantData{

	 /** Default log. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    
    String parentPath;
    
    /*Storing values into Jcr*/
	@Override
	public void insertVariantData(CarVariant variant, ResourceResolver rr) {

		try {
			Session sessionVariant = rr.adaptTo(Session.class);
			Node root = sessionVariant.getRootNode();

			parentPath = variant.getParentPath();
			Node pathToVariant = root.getNode(parentPath);

			Node variantNode = pathToVariant.addNode(variant.getGrade(), "nt:unstructured");
			variantNode.setProperty("grade", variant.getGrade());
			variantNode.setProperty("description", "Model Type");
			if (!(variant.getSeater()).equalsIgnoreCase("5")) {
				variantNode.setProperty("seater", variant.getSeater());
			}
			if (!(variant.getTransmission()).equalsIgnoreCase("NA")) {
				variantNode.setProperty("transmission", variant.getTransmission());
			}
			sessionVariant.save();
		} catch (Exception e) {
			log.error("Catch block of InsertVariantDataImpl" + e.getMessage());
		}
		
	}

}
