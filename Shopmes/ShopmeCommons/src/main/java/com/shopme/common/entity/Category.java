package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "catergories")
public class Category 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int ID;
	
	@Column(length = 128, nullable = false, unique = true)
	protected String name;
	
	@OneToOne
	@JoinColumn(name = "parentid")
	protected Category parent;
	
	@OneToMany(mappedBy = "parent")
	protected Set<Category> childcater = new HashSet<>();

	@Transient
	protected boolean hasChild;
	
	public Category(String name) 
	{
		this.name = name;
	}
	

	public Category() 
	{
		
	}

	public Category(int id) 
	{
		this.ID = id;
	}

	public static Category copyIDANDNAME(Category category)
	{
		Category copycategory = new Category();
		copycategory.setID(category.getID());
		copycategory.setName(category.getName());
		
		return copycategory;
	}
	
	public static Category copyIDANDNAME(int ID , String name)
	{
		Category copycategory = new Category();
		copycategory.setID(ID);
		copycategory.setName(name);
		
		return copycategory;
	}
	
	public static Category copyFull(Category category)
	{
		Category copycategory = new Category();
		copycategory.setID(category.getID());
		copycategory.setName(category.getName());
		copycategory.setHasChild(category.getChildcater().size() > 0);
		
		return copycategory;
	}
	
	public static Category copyFull(Category category, String name)
	{
		Category copycategory = new Category();
		copycategory.setID(category.getID());
		copycategory.setName(name);
		
		
		return copycategory;
	}
	
	public Category(String name, Category parent) 
	{
		this(name);
		this.parent = parent;
	}

	public int getID() 
	{
		return ID;
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

	public Category getParent() 
	{
		return parent;
	}

	public void setParent(Category parent) 
	{
		this.parent = parent;
	}

	public Set<Category> getChildcater() 
	{
		return childcater;
	}

	public void setChildcater(Set<Category> childcater) 
	{
		this.childcater = childcater;
	}
	
	public boolean isHasChild()
	{
		return hasChild;
	}
	
	public void setHasChild(boolean haschild)
	{
		this.hasChild = haschild;
	}
	
}
