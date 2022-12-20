package com.shopme.common.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
	@Column(name="main_image")
	protected String mainImage;
	
	@Column(name="extra_image")
	protected String extraImage;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	protected Set<ProductImage> images = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "catergory_id")
	protected Category category;

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

	public Category getCategory() 
	{
		return category;
	}

	public void setCategory(Category category) 
	{
		this.category = category;
	}
	
	@Override
	public String toString() 
	{
		return "Product [ID=" + ID + ", name=" + name + "]";
	}

	
	public String getMainImage() 
	{
		return mainImage;
	}

	public void setMainImage(String mainImage) 
	{
		this.mainImage = mainImage;
	}

	public Set<ProductImage> getImages() 
	{
		return images;
	}

	public void setImages(Set<ProductImage> images) 
	{
		this.images = images;
	}

	public void addExtraimages(String imagename)
	{
		this.images.add(new ProductImage(imagename , this));
	}
	
	@Transient
	public String getMainImagePath()
	{
		if (ID == 0 || mainImage == null)
		{
			return "/images/image-thumbnail.png";
		}
		
		return "/product-images/" + this.ID + "/" + this.mainImage;
	}
	
	@Transient
	public String getExtraImagePath()
	{
		if (ID == 0 || extraImage == null)
		{
			return "/images/image_thumbnail_blue.png";
		}
		
		return "/product-images/" + this.ID + "/" + this.extraImage;
	}
}
