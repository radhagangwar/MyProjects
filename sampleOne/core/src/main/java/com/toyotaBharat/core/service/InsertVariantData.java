package com.toyotaBharat.core.service;

import org.apache.sling.api.resource.ResourceResolver;
import com.toyotaBharat.core.models.CarVariant;

public interface InsertVariantData {

	public void insertVariantData(CarVariant variant, ResourceResolver rr);
}
