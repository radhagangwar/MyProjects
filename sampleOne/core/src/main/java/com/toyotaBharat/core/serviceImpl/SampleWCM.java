package com.toyotaBharat.core.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.toyotaBharat.core.service.SampleModel;

public class SampleWCM extends WCMUse {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	Map<SampleModel, SampleModel> sample = null;

	String reversed;

	@Override
	public void activate() throws Exception {
		//using sling Sling Helper class getService() method
		Sample osgi = getSlingScriptHelper().getService(Sample.class);
		//getting the data from service
		sample = osgi.getData();
		log.info("data in activate method" + sample);
	}

	public Map<SampleModel, SampleModel> getSample() {
		log.info("data in geSample() method" + sample);
		return sample;
	}

}
