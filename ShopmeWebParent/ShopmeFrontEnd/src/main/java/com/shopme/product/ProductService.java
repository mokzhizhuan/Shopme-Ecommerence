package com.shopme.product;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shopme.category.CategoryNotFoundException;
import com.shopme.category.CategoryRepository;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Products;

@Service
public class ProductService {
	public static final int PRODUCTS_PER_PAGE = 10;
	public static final int SEARCH_RESULTS_PER_PAGE = 10;
	
	@Autowired 
	private ProductRepository repo;
	
	@Autowired
	private CategoryRepository caterrepo;
	
	public List<Products> listByCategory(Integer categoryId) {
		String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
		
		return repo.listByCategory(categoryId, categoryIdMatch);
	}
	
	public Products getProduct(String alias) throws ProductNotFoundException {
		Products product = repo.findByAlias(alias);
		if (product == null) {
			throw new ProductNotFoundException("Could not find any product with alias " + alias);
		}
		
		return product;
	}
	
	public List<Products> listAll()
	{
		return (List<Products>) repo.findAll();
	}
}
