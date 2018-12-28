package com.toyotaBharat.core.wcmimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toyotaBharat.core.models.CarModel;
import com.toyotaBharat.core.service.HandleModelForm;
import com.toyotaBharat.core.service.InsertModelData;
import com.toyotaBharat.core.serviceImpl.InsertModelDataImpl;

import java.util.HashMap;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
  
@Component
@Service
public class HandleModelFormImpl implements HandleModelForm{

	 /** Default log. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Reference
	private ResourceResolverFactory resourceFactory;


    /*
     * Receive parameters from form via customSubmit in this method
     * and set it's value in Pojo class. And then send a 'model' object 
     * which have all the parameter mapped in it & array for 'model types' 
     * & 'resourceResolver' 
     * */
	@Override
	public void handleModelFormData(String modelName,String[] modelTypes) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, "readService");		
		CarModel model = new CarModel();
		model.setModelName(modelName);
		
		ResourceResolver rr;
		try {
			rr = resourceFactory.getServiceResourceResolver(paramMap);
			InsertModelData imd =  new InsertModelDataImpl();
			imd.insertModelData(model,modelTypes,rr); 
			
		} catch (Exception e) {
			log.error("Catch block of HandleModelFormImpl" + e.getMessage());
		}
	}
  
}