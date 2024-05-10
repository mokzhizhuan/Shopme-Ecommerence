package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "brands")
public class Brands extends IdIdentify 
{
	
	@Column(length = 45, nullable = false, unique = true)
	private String name;
	
	@Column(length = 128)
	private String logo;
	
	@ManyToMany
	@JoinTable(
			name = "brands_categories",
			joinColumns = @JoinColumn(name = "brand_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id")
			)
	public Set<Category> categories = new HashSet<>();
	

	
	public Brands(String name) {
		this.name = name;
		this.logo = "default.png";
	}

	public Brands() {
		
	}
	
	public Brands(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Brands(Integer id, String name, String logo, Set<Category> categories) {
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.categories = categories;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "Brands [id=" + id + ", name=" + name + ", logo=" + logo + ", categories=" + categories + "]";
	}
	
	@Transient
	public String getLogoPath()
	{
		if(this.id == null || this.logo == null)
		{
			return "/images/image-thumbnail.png";
		}
		
		return "/brands-logos/" + this.id + "/" + this.logo;
	}
}
