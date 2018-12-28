package com.toyotaBharat.core.wcmimpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.toyotaBharat.core.models.Cars;
import com.toyotaBharat.core.service.CarModel;

public class CarModelWCM extends WCMUse{
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	Map<Cars, Cars> carModelsData = null;

	@Override
	public void activate() throws Exception {
		CarModel osgi = getSlingScriptHelper().getService(CarModel.class);
		//getting the data from service
		carModelsData = osgi.getCarModelData();
		log.info("data in activate method" + osgi);
		
	}
	
	public Map<Cars, Cars> getCarModelsData() {
		log.info("data in getCarModelsData() method" + carModelsData);
		return carModelsData;
	}

}
