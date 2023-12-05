package com.shopme.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "product")
public class Products {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@Column(length = 128, nullable = false, unique = true)
	private String name; 
	
	@Column(length = 128, nullable = false, unique = true)
	public String ailas;
	
	@Column(length = 256, nullable = false, name="shortdescription")
	private String shortDes;
	
	@Column(length = 512, nullable = false, name="fulldescription")
	private String Description;
	
	@Column(name = "created_time")
	private Date createdTIme;
	
	@Column(name = "updated_time")
	private Date updatedTime;
	
	private boolean enabled;
	
	@Column(name = "in_stock")
	private boolean inStock;
	
	@Column(name = "price")
	private float price;
	
	@Column(name = "discount_cost")
	private float discountcost;
	
	@Column(name="mainimage")
	private String mainImage;
	
	private float length;
	private float width;
	private float height;
	private float weight;
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brands brand;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
	private Set<ProductImage> images = new HashSet<>();
	
	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
	private List<ProductDetail> details = new ArrayList<>();

	public Products(Integer id) {
		this.Id = id;
	}

	public Products() 
	{
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		this.Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAilas() {
		return ailas;
	}

	public void setAilas(String ailas) {
		this.ailas = ailas;
	}

	public String getShortDes() {
		return shortDes;
	}

	public void setShortDes(String shortDes) {
		this.shortDes = shortDes;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Date getCreatedTIme() {
		return createdTIme;
	}

	public void setCreatedTIme(Date createdTIme) {
		this.createdTIme = createdTIme;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public float getDiscountcost() {
		return discountcost;
	}

	public void setDiscountcost(float discountcost) {
		this.discountcost = discountcost;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public Brands getBrand() {
		return brand;
	}

	public void setBrand(Brands brand) {
		this.brand = brand;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Products [Id=" + Id + ", name=" + name + "]";
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public Set<ProductImage> getImages() {
		return images;
	}

	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}
	
	public void addExtraImages(String extrasimagename) {
		this.images.add(new ProductImage(extrasimagename, this));
	} 
	
	public List<ProductDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ProductDetail> details) {
		this.details = details;
	}

	@Transient
	public String getPhotosImagePath()
	{
		if(Id == null || mainImage  == null)
		{
			return "/images/default-image.png";
		}
		
		return "product-images/" + this.Id + "/" + this.mainImage;
	}

	public void addDetail(String name, String value) {
		this.details.add(new ProductDetail(name, value, this));
	}

	public void addDetail(Integer id, String name, String value) {
		this.details.add(new ProductDetail(id, name, value, this));
	}
}
