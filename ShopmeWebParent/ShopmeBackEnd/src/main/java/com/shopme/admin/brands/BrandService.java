package com.shopme.admin.brands;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.admin.category.CategoryRepository;
import com.shopme.common.entity.Brands;
import com.shopme.common.entity.Category;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BrandService 
{
	@Autowired
	private BrandRepository Brandrepo;
	
	
	public List<Brands> ListAll()
	{
		return (List<Brands>)Brandrepo.findAll();
	}

	public Brands save(Brands brand) 
	{
		return Brandrepo.save(brand);
	}
	
	public Brands get(Integer id) throws BrandNotFoundException
	{
		try
		{
			return Brandrepo.findById(id).get();
		}
		catch(NoSuchElementException ex)
		{
			throw new BrandNotFoundException("Could not find any brand with ID" + id);
		}
	}
	
	public void delete(Integer id) throws BrandNotFoundException
	{
		Long countbyid = Brandrepo.countById(id);
		
		if(countbyid == null || countbyid == 0 )
		{
			throw new BrandNotFoundException("Could not find any brand with ID" + id);
		}
		
		Brandrepo.deleteById(id);
	}
	
	public boolean isNameUnique(int id, String name) 
	{
		Brands brandbyName = Brandrepo.getBrandByName(name);
		
		if (brandbyName == null) return true;
		
		boolean isCreatingNew = (id == 0);
		
		if(isCreatingNew)
		{
			if(brandbyName != null) 
			{
				return false;
			}
			else
			{
				if(brandbyName.getId() != id)
				{
					return false;
				}
			}
		}
		
		return true;
	}
}
