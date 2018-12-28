package com.toyotaBharat.core.wcmimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.toyotaBharat.core.models.BasicCarDetailsModel;
import com.toyotaBharat.core.service.BasicCarDetails;
import com.toyotaBharat.core.util.AcmeUtils;

public class BasicCarDetailsWCM extends WCMUse{
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	List<BasicCarDetailsModel> basicCarDetails = null;

	@Override
	public void activate() throws Exception {
		String pagePath = get("pagePath", String.class);
		AcmeUtils acmeUtils = new AcmeUtils();
		String carModelName = acmeUtils.getCarModelName(pagePath);
		BasicCarDetails osgi = getSlingScriptHelper().getService(BasicCarDetails.class);
		//getting the data from service
		basicCarDetails = osgi.getBasicCarDetails(carModelName);
		log.info("data in activate method" + osgi);
		
	}
	
	public List<BasicCarDetailsModel> getBasicCarDetails() {
		log.info("data in geSample() method" + basicCarDetails);
		return basicCarDetails;
	}

}
