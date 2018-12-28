package com.toyotaBharat.core.wcmimpl;

import java.util.ArrayList;
import java.util.List;

import com.adobe.cq.sightly.WCMUse;
import com.toyotaBharat.core.service.ColorSelector;

public class ColorSelWcmUse extends WCMUse {

	private String imagePath;

	List<String> colorImgagesList = new ArrayList<>();
	@Override
	public void activate() throws Exception {

		String path = get("carpath", String.class);
		imagePath = new StringBuilder(path).toString();
		ColorSelector osgi = getSlingScriptHelper().getService(ColorSelector.class);
		colorImgagesList = osgi.getImages(imagePath);
	}

	public String getReversed() {
		return imagePath;
	}

	public List<String> getColorImgagesList() {
		return colorImgagesList;
	}
	
}	
