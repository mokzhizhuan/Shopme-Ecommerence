package com.shopme.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int ID;
	
	@Column(length = 40, nullable = false, unique = true)
	protected String name;
	
	@Column(length = 150, nullable = false)
	protected String descrption;

	public Role() 
	{

	}
	
	public Role(String name, String descrption) 
	{
		this.name = name;
		this.descrption = descrption;
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

	public String getDescrption() 
	{
		return descrption;
	}

	public void setDescrption(String descrption) 
	{
		this.descrption = descrption;
	}

	@Override
	public String toString() 
	{
		return this.name;
	}

}
