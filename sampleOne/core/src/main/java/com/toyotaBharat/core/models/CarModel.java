package com.toyotaBharat.core.models;

public class CarModel {
	
	private String modelName;	

	public CarModel(){
		super();
	}
	
	public CarModel(String modelName){
		super();
		this.modelName = modelName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
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
