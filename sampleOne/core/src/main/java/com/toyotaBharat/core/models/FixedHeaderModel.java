package com.toyotaBharat.core.models;

public class FixedHeaderModel {

	private String pagePath;
	private String pageName;
	private String pageTitle;
	
	
	public FixedHeaderModel() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pageName == null) ? 0 : pageName.hashCode());
		result = prime * result + ((pagePath == null) ? 0 : pagePath.hashCode());
		result = prime * result + ((pageTitle == null) ? 0 : pageTitle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FixedHeaderModel other = (FixedHeaderModel) obj;
		if (pageName == null) {
			if (other.pageName != null)
				return false;
		} else if (!pageName.equals(other.pageName))
			return false;
		if (pagePath == null) {
			if (other.pagePath != null)
				return false;
		} else if (!pagePath.equals(other.pagePath))
			return false;
		if (pageTitle == null) {
			if (other.pageTitle != null)
				return false;
		} else if (!pageTitle.equals(other.pageTitle))
			return false;
		return true;
	}

	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public FixedHeaderModel(String pagePath, String pageName, String pageTitle) {
		super();
		this.pagePath = pagePath;
		this.pageName = pageName;
		this.pageTitle = pageTitle;
	}
	
	
}
