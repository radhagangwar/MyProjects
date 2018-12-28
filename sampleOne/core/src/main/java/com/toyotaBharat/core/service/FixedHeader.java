package com.toyotaBharat.core.service;

import java.util.Map;

import com.day.cq.wcm.api.Page;

import com.toyotaBharat.core.models.FixedHeaderModel;

public interface FixedHeader {
	
	public Map<String,FixedHeaderModel> fixedHeaderPages();
	
	public Page getHeaderPages();
}
