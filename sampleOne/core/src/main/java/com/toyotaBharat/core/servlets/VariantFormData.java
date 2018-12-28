package com.toyotaBharat.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toyotaBharat.core.models.CarVariant;
import com.toyotaBharat.core.service.InsertVariantData;
import com.toyotaBharat.core.serviceImpl.InsertVariantDataImpl;


@SuppressWarnings("serial")
@SlingServlet(paths = "/bin/variantFormData", methods = "POST")
public class VariantFormData extends SlingAllMethodsServlet{

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	 @Reference
	private ResourceResolverFactory resourceFactory;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		
		try{
			//Get data from the form i.e. variantForm
			String selectedModel = request.getParameter("selectedModel");
			String selectedModelType = request.getParameter("selectedModelType");
			String selectedSeater = request.getParameter("selectedSeater");
			String selectedTransmission = request.getParameter("selectedTransmission");
			String grade = request.getParameter("grade");
			String parentPath = selectedModel + "/" + selectedModelType;
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ResourceResolverFactory.SUBSERVICE, "readService");
			
			//Set received values into Pojo calss of Variant
			CarVariant variant = new CarVariant();
			variant.setParentPath(parentPath);
			variant.setGrade(grade);
			variant.setSeater(selectedSeater);
			variant.setTransmission(selectedTransmission);
			
			ResourceResolver rr;
			try {
				rr = resourceFactory.getServiceResourceResolver(paramMap);
				//Call to service layer
				InsertVariantData ivd = new InsertVariantDataImpl();
				ivd.insertVariantData(variant,rr); 
				
			} catch (Exception e) {
				log.error("Catch block of VariantFormData" + e.getMessage());
			}
			
		} catch (Exception e) {
			log.error("Catch block of VariantFormData" + e.getMessage());
		}		
	}
	
}
