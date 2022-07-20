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

@Entity
@Table(name = "catergories")
public class Catergory 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int ID;
	
	@Column(length = 128, nullable = false, unique = true)
	protected String name;
	
	@OneToOne
	@JoinColumn(name = "parentid")
	protected Catergory parent;
	
	@OneToMany(mappedBy = "parent")
	protected Set<Catergory> childcater = new HashSet<>();

	public Catergory(String name) 
	{
		this.name = name;
	}
	

	public Catergory() 
	{
		
	}

	public Catergory(int id) 
	{
		this.ID = id;
	}

	public static Catergory copyIDANDNAME(Catergory category)
	{
		Catergory copycategory = new Catergory();
		copycategory.setID(category.getID());
		copycategory.setName(category.getName());
		
		return copycategory;
	}
	
	public static Catergory copyIDANDNAME(int ID , String name)
	{
		Catergory copycategory = new Catergory();
		copycategory.setID(ID);
		copycategory.setName(name);
		
		return copycategory;
	}
	
	public static Catergory copyFull(Catergory category)
	{
		Catergory copycategory = new Catergory();
		copycategory.setID(category.getID());
		copycategory.setName(category.getName());
		
		
		return copycategory;
	}
	
	public static Catergory copyFull(Catergory category, String name)
	{
		Catergory copycategory = new Catergory();
		copycategory.setID(category.getID());
		copycategory.setName(name);
		
		
		return copycategory;
	}
	
	public Catergory(String name, Catergory parent) 
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

	public Catergory getParent() 
	{
		return parent;
	}

	public void setParent(Catergory parent) 
	{
		this.parent = parent;
	}

	public Set<Catergory> getChildcater() 
	{
		return childcater;
	}

	public void setChildcater(Set<Catergory> childcater) 
	{
		this.childcater = childcater;
	}
	
	
}
