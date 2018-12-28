package com.toyotaBharat.core.wcmimpl;

import java.util.ArrayList;
import java.util.List;

import com.adobe.cq.sightly.WCMUse;
import com.toyotaBharat.core.service.Gallery;

public class GalleryWcmUse extends WCMUse {

	private String imagePath;

	List<String> imageList = new ArrayList<>();

	@Override
	public void activate() throws Exception {

		String path = get("path", String.class);
		imagePath = new StringBuilder(path).toString();
		Gallery osgi = getSlingScriptHelper().getService(Gallery.class);
		imageList = osgi.getImage(imagePath);

	}

	public String getReversed() {
		return imagePath;
	}

	public List<String> getImageList() {
		return imageList;
	}

}
