package com.toyotaBharat.core.models;

public class ImageModel {

	private String imagePath;
	private String imageDescription;
	private String imageTitle;
	private String parentName;
	private String parentTitle;
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getImageDescription() {
		return imageDescription;
	}
	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}
	
	public String getImageTitle() {
		return imageTitle;
	}
	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}
	
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public String getParentTitle() {
		return parentTitle;
	}
	public void setParentTitle(String parentTitle) {
		this.parentTitle = parentTitle;
	}	
}
