package com.shopme.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int ID;
	
	@Column(length = 128, nullable = false, unique = true)
	protected String name;
	
	@Column(length = 4096)
	protected String Description;

	@Column(name = "created_time")
	protected Date createdTime;
	
	@Column(name = "updated_time")
	protected Date updatedTime;
	
	protected boolean enabled;
	
	@Column(name = "in_stock")
	protected boolean inStock;
	
	protected float cost;
	
	protected float discount;
	
	protected float length;
	
	protected float width;
	
	protected float height;
	
	protected float weight;
	
	@ManyToOne
	@JoinColumn(name = "catergory_id")
	protected Catergory category;

	public int getID() 
	{
		return ID;
	}

	public Product() 
	{
		
	}

	public void setID(int iD) 
	{
		ID = iD;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getDescription() 
	{
		return Description;
	}

	public void setDescription(String description) 
	{
		Description = description;
	}

	public Date getCreatedTime() 
	{
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) 
	{
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() 
	{
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime)
	{
		this.updatedTime = updatedTime;
	}

	public boolean isEnabled() 
	{
		return enabled;
	}

	public void setEnabled(boolean enabled) 
	{
		this.enabled = enabled;
	}

	public boolean isInStock() 
	{
		return inStock;
	}

	public void setInStock(boolean inStock) 
	{
		this.inStock = inStock;
	}

	public float getCost() 
	{
		return cost;
	}

	public void setCost(float cost) 
	{
		this.cost = cost;
	}

	public float getDiscount() 
	{
		return discount;
	}

	public void setDiscount(float discount) 
	{
		this.discount = discount;
	}

	public float getLength() 
	{
		return length;
	}

	public void setLength(float length) 
	{
		this.length = length;
	}

	public float getWidth() 
	{
		return width;
	}

	public void setWidth(float width) 
	{
		this.width = width;
	}

	public float getHeight() 
	{
		return height;
	}

	public void setHeight(float height) 
	{
		this.height = height;
	}

	public float getWeight() 
	{
		return weight;
	}

	public void setWeight(float weight)
	{
		this.weight = weight;
	}

	public Catergory getCategory() 
	{
		return category;
	}

	public void setCategory(Catergory category) 
	{
		this.category = category;
	}
	
	@Override
	public String toString() 
	{
		return "Product [ID=" + ID + ", name=" + name + "]";
	}
}
