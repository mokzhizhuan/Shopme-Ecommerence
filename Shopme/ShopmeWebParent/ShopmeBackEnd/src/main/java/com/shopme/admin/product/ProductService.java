package com.shopme.admin.product;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.shopme.admin.catergories.CategoryNotFoundException;
import com.shopme.common.entity.Product;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProductService 
{
	@Autowired
	private ProductRepository Productrepo;
	
	public List<Product> listAll()
	{
		return (List<Product>) Productrepo.findAll();
	}
	
	public Product save(Product product)
	{
		if (product.getID() == 0)
		{
			product.setCreatedTime(new Date());
			product.setUpdatedTime(new Date());
		}
		
		return Productrepo.save(product);
	}
	
	public String checkUnique(int ID , String name)
	{
		boolean isCreatingNew = (ID == 0);
		Product productByName = Productrepo.findByName(name);
		if (isCreatingNew)
		{
			if (productByName != null)
			{
				return "Duplicate";
			}
		}
		else 
		{
			if (productByName != null && productByName.getID() != ID)
			{
				return "Duplicate";
			}
		}
		
		return "OK";
	}

	public void updateProductEnabledStatus(int ID, boolean enabled) 
	{
		Productrepo.updateEnabledStatus(ID, enabled);
	}

	public void delete(int ID) throws ProductNotFoundException 
	{
		Long countID = Productrepo.countByID(ID);
		if(countID == null || countID == 0)
		{
			throw new ProductNotFoundException("Product is not found. Product ID : " + ID);
		}
		else
		{
			Productrepo.deleteById(ID);
		}
	}
	
	public Product get(int ID) throws ProductNotFoundException
	{
		try
		{
			return Productrepo.findById(ID).get();
		}
		catch(NoSuchElementException ex)
		{
			throw new ProductNotFoundException("Could not find any produc with ID " + ID);
		}
	}
}
