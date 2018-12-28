package com.toyotaBharat.core.wcmimpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.day.cq.wcm.api.Page;
import com.toyotaBharat.core.models.FixedHeaderModel;
import com.toyotaBharat.core.service.FixedHeader;

public class FixedHeaderWCM extends WCMUse{

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	Map<String,FixedHeaderModel> fixedPages;
	Page headerPages;
	
	@Override
	public void activate() throws Exception {
		FixedHeader fixedHeader=getSlingScriptHelper().getService(FixedHeader.class);
		fixedPages=fixedHeader.fixedHeaderPages();
		headerPages=fixedHeader.getHeaderPages();
		
	}
	
	public Map<String,FixedHeaderModel> getFixedPages() {		
		return fixedPages;
	}
	public Page getHeaderPages() {		
		return headerPages;
	}

}
