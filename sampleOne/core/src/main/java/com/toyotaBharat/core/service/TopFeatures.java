package com.toyotaBharat.core.service;

import java.util.List;
import com.toyotaBharat.core.models.ImageModel;

public interface TopFeatures {
	List<ImageModel> populateDamImages(String carModelName);
}
