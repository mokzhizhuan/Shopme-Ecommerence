package com.shopme.admin.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.catergories.CatergoryService;
import com.shopme.common.entity.Category;
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
		List<Category> listCategory = categoryservice.listAll();
		
		Product product = new Product();
		
		product.setEnabled(true);
		
		product.setInStock(true);
		
		String title = "Create New Product";
		
		model.addAttribute("product", product);
		model.addAttribute("listCategories", listCategory);
		model.addAttribute("PageTitle", title);
		
		return "product/product_form";
	}
	
	@PostMapping("/products/save")
	public String saveNewProduct(Product product, RedirectAttributes redirect) 
	{
		productservice.save(product);
		redirect.addFlashAttribute("message", "The product : (" + product.getName() + ") has been saved successfully. ");
		return "redirect:/products";
	}
	
	@GetMapping("/products/{ID}/enabled/{status}")
	public String updateCategoryEnabledStatus(@PathVariable(name = "ID") int ID , 
			@PathVariable(name = "status") boolean enabled, RedirectAttributes redirectAttributes)
	{
		productservice.updateProductEnabledStatus(ID, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The Product ID: (" + Integer.toString(ID) +  ") has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		
		return "redirect:/products";
	}
	
	@GetMapping("/products/delete/{ID}")
	public String deleteproduct(@PathVariable(name = "ID") int ID, Model model,
			RedirectAttributes redirectAttributes)
	{
		try
		{
			productservice.delete(ID);
			redirectAttributes.addFlashAttribute("message", "The Product ID : " + ID +  " is deleted successfully");
			return "redirect:/products";
		}
		catch(ProductNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/products";
		}
	}
	
	@GetMapping("/products/edit/{ID}")
	public String EditProduct(@PathVariable(name = "ID") int ID, Model model,
			RedirectAttributes redirectAttributes)
	{
		try
		{
			Product product = productservice.get(ID);
			List<Category> listCategory = categoryservice.listAll();
			
			String title = "Edit Product (ID: " + ID + ")";
			model.addAttribute("product", product);
			model.addAttribute("listCategories", listCategory);
			model.addAttribute("PageTitle", title);
			return "product/edit_product_form";
		}
		catch(ProductNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "products";
		}

	}
}
