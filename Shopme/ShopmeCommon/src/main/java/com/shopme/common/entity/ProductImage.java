package com.shopme.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_images")
public class ProductImage 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int ID;
	
	@Column(nullable = false)
	protected String name;
	
	
	@ManyToOne
	@JoinColumn
	private Product product;
	
	
	public ProductImage(String name, Product product) 
	{
		this.name = name;
		this.product = product;
	}

	public String getName()
	{
		return name;
	}


	public void setName(String name) 
	{
		this.name = name;
	}


	public Product getProduct() 
	{
		return product;
	}

	public void setProduct(Product product) 
	{
		this.product = product;
	}
}
