package com.toyotaBharat.core.models;

public class Cars {

	private String carName;
	private String carType;
	private String carImagePath;
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarImagePath() {
		return carImagePath;
	}
	public void setCarImagePath(String carImagePath) {
		this.carImagePath = carImagePath;
	}
	public Cars(String carName, String carType, String carImagePath) {
		super();
		this.carName = carName;
		this.carType = carType;
		this.carImagePath = carImagePath;
	}
	public Cars() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carImagePath == null) ? 0 : carImagePath.hashCode());
		result = prime * result + ((carName == null) ? 0 : carName.hashCode());
		result = prime * result + ((carType == null) ? 0 : carType.hashCode());
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
		Cars other = (Cars) obj;
		if (carImagePath == null) {
			if (other.carImagePath != null)
				return false;
		} else if (!carImagePath.equals(other.carImagePath))
			return false;
		if (carName == null) {
			if (other.carName != null)
				return false;
		} else if (!carName.equals(other.carName))
			return false;
		if (carType == null) {
			if (other.carType != null)
				return false;
		} else if (!carType.equals(other.carType))
			return false;
		return true;
	}
	
	
}
