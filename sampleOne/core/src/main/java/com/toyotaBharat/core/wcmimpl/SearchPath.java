package com.toyotaBharat.core.wcmimpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.toyotaBharat.core.service.QueryModelPath;

public class SearchPath extends WCMUse{
	
	private Map<String,String> model;
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void activate() throws Exception {
		QueryModelPath queryModelPath = getSlingScriptHelper().getService(QueryModelPath.class);
		model = queryModelPath.getModels();
	}
	
	public Map<String,String> getModel() {
		return model;
	}
}
