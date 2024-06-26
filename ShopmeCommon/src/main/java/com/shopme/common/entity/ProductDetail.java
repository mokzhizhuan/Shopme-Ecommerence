package com.shopme.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_details")
public class ProductDetail extends IdIdentify {
	
	
	@Column(length = 255, nullable = false)
	private String name; 
	
	@Column(length = 255, nullable = false)
	private String value;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Products product;
	
	public ProductDetail()
	{
		
	}

	public ProductDetail(String name, String value, Products product) {
		this.name = name;
		this.value = value;
		this.product = product;
	}
	public ProductDetail(Integer id, String name, String value, Products product) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}
	
}
