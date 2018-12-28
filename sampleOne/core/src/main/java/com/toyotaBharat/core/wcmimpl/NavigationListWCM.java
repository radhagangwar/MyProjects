package com.toyotaBharat.core.wcmimpl;

import com.adobe.cq.sightly.WCMUse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import com.toyotaBharat.core.util.AcmeUtils;
import com.toyotaBharat.core.models.NavigationListModel;
import com.toyotaBharat.core.service.NavigationList;

public class NavigationListWCM extends WCMUse {
    
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	List<NavigationListModel> navList;
	
	@Override
	public void activate() throws Exception {		
		 NavigationList nav = getSlingScriptHelper().getService(NavigationList.class);
		 String pagePath = get("pagePath", String.class);		 
		 AcmeUtils acmeUtils = new AcmeUtils();
		 String carModelName = acmeUtils.getCarModelName(pagePath);		 
		 log.info("Car Model Name:" + carModelName);		 
		 navList = nav.getNavList(carModelName);				 
	}
	
	public List<NavigationListModel> getNavList() { 		
		return navList;
	}
}


