package com.toyotaBharat.core.service;

public class SampleModel {
	
	private String carModelName;
	private int carModelType;
	private int carModelPrice;
	private String variantName;
	private String state;
	public String getCarModelName() {
		return carModelName;
	}
	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}
	public int getCarModelType() {
		return carModelType;
	}
	public void setCarModelType(int carModelType) {
		this.carModelType = carModelType;
	}
	public int getCarModelPrice() {
		return carModelPrice;
	}
	public void setCarModelPrice(int carModelPrice) {
		this.carModelPrice = carModelPrice;
	}
	public String getVariantName() {
		return variantName;
	}
	public void setVariantName(String variantName) {
		this.variantName = variantName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public SampleModel(String carModelName, int carModelType, int carModelPrice, String variantName, String state) {
		super();
		this.carModelName = carModelName;
		this.carModelType = carModelType;
		this.carModelPrice = carModelPrice;
		this.variantName = variantName;
		this.state = state;
	}
	public SampleModel() {
		super();
	}
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	

}
