package com.shopme.admin.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.admin.catergories.CatergoryService;
import com.shopme.common.entity.Catergory;
import com.shopme.common.entity.Product;

@Controller
public class ProductController 
{
	@Autowired
	private ProductService productservice;
	
	@Autowired
	private CatergoryService categoryservice;
	
	@GetMapping("/products")
	public String listAll(Model model)
	{
		List<Product> listProduct = productservice.listAll();
		model.addAttribute("listProduct",listProduct);
		return "product/products";
	}
	
	@GetMapping("/products/new")
	public String newProduct(Model model)
	{
		List<Catergory> listCategory = categoryservice.listAll();
		
		Product product = new Product();
		
		product.setEnabled(true);
		
		product.setInStock(true);
		
		String title = "Create New Product";
		
		model.addAttribute("product", product);
		model.addAttribute("listCategories", listCategory);
		model.addAttribute("pageTitle", title);
		
		return "product/product_form";
	}
}
