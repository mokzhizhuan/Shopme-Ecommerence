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
	
	public Page<Products> listByCategory(int pageNum, Integer categoryId) {
		String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		
		return repo.listByCategory(categoryId, categoryIdMatch, pageable);
	}
	
	public Products getProduct(String alias) throws ProductNotFoundException {
		Products product = repo.findByAilas(alias);
		if (product == null) {
			throw new ProductNotFoundException("Could not find any product with alias " + alias);
		}
		
		return product;
	}
	
	public Page<Products> search(String keyword, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);
		return repo.search(keyword, pageable);
		
	}

	public List<Products> listAll()
	{
		return (List<Products>) repo.findAll();
	}
}
