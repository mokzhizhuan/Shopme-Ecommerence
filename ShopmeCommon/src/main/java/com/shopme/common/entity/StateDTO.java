package com.shopme.common.entity;

public class StateDTO extends IdIdentify{
	private String name;
	
	public StateDTO() {
		
	}
	
	public StateDTO(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
