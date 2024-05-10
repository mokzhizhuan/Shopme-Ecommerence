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
@Table(name = "productimage")
public class ProductImage extends IdIdentify {

	
	@Column(length = 128, nullable = false, unique = true)
	private String name; 
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Products product;
	
	public ProductImage()
	{
		
	}
	
	public ProductImage(String name, Products product) {
		this.name = name;
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}
	
}
