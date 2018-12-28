package com.toyotaBharat.core.service;

import java.util.List;

import com.toyotaBharat.core.models.BasicCarDetailsModel;

public interface BasicCarDetails {

	List<BasicCarDetailsModel> getBasicCarDetails(String carModelName);

}
