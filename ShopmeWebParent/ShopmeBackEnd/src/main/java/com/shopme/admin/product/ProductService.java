package com.shopme.admin.product;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shopme.admin.brands.BrandNotFoundException;
import com.shopme.admin.category.CategoryNotFoundException;
import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.Products;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	public static final int PRODUCTS_PER_PAGE = 5;
	
	@Autowired
	private ProductRepository productrepo;

	public List<Products> listAll()
	{
		return (List<Products>) productrepo.findAll();
	}
	
	public Products save(Products product) 
	{
		if(product.getId() == null)
		{
			product.setCreatedTIme(new Date());
		}
		
		if(product.getAilas() == null || product.getAilas().isEmpty())
		{
			String Alias = product.getName().replaceAll(" ", "-");
			product.setAilas(Alias);
		}
		else
		{
			product.setAilas(product.getAilas().replaceAll(" ", "-"));
		}
		
		product.setUpdatedTime(new Date());
		
		return productrepo.save(product);
	}
	
	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Products productByName = productrepo.findByName(name);
		
		if (isCreatingNew) {
			if (productByName != null) return "Duplicate";
		} else {
			if (productByName != null && productByName.getId() != id) {
				return "Duplicate";
			}
		}
		
		return "OK";
	}
	
	public void updateEnabledStatus(int id , boolean enabled)
	{
		productrepo.updateEnabledStatus(id, enabled);
	}
	
	public void delete(Integer id) throws ProductNotFoundException
	{
		Long countbyID = productrepo.countById(id);
		if(countbyID == null || countbyID == 0)
		{
			throw new ProductNotFoundException("Could not find any product with ID " + id);
		}
		
		productrepo.deleteById(id);
	}

	public Products get(Integer id) throws ProductNotFoundException {
		try
		{
			return productrepo.findById(id);
		}
		catch(NoSuchElementException ex)
		{
			throw new ProductNotFoundException("Could not find any brand with ID" + id);
		}
	}
	
	public void searchProducts(int pageNum, PagingAndSortingHelper helper) {
		Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
		String keyword = helper.getKeyword();		
		Page<Products> page = productrepo.searchProductsByName(keyword, pageable);		
		helper.updateModelAttributes(pageNum, page);
	}
}
