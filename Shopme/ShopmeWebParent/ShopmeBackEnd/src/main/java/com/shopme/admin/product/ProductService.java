package com.shopme.admin.product;

import java.util.List;

import org.springframework.stereotype.Service;

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
}
