package com.toyotaBharat.core.service;

import org.apache.sling.api.resource.ResourceResolver;

import com.toyotaBharat.core.models.CarModel;

public interface InsertModelData {
	
	public void insertModelData(CarModel model,String[] modelTypes, ResourceResolver rr);
}
