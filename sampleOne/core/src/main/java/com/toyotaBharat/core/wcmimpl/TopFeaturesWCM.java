package com.toyotaBharat.core.wcmimpl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.toyotaBharat.core.util.AcmeUtils;
import com.toyotaBharat.core.models.ImageModel;
import com.toyotaBharat.core.service.*;

public class TopFeaturesWCM extends WCMUse {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	List<ImageModel> imageUrlDescription;
	
	@Override
	public void activate() throws Exception {		
		TopFeatures topFeatures = getSlingScriptHelper().getService(TopFeatures.class);
		String pagePath = get("pagePath", String.class);
		AcmeUtils acmeUtils = new AcmeUtils();
		String carModelName = acmeUtils.getCarModelName(pagePath);
		log.info("Car Model Name:::::::::" + carModelName);
		imageUrlDescription = topFeatures.populateDamImages(carModelName);
		log.info("imageUrlDescription in Activate:::::::::" + imageUrlDescription);
	}

	public List<ImageModel> getImageUrlDescription() {
		log.info("imageUrlDescription in getImageUrlDescription:::::::::" + imageUrlDescription);
		return imageUrlDescription;
	}
		
}
