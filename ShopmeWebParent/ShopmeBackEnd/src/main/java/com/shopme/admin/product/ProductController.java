package com.shopme.admin.product;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.brands.BrandService;
import com.shopme.admin.category.CategoryNotFoundException;
import com.shopme.admin.category.CategoryService;
import com.shopme.common.entity.Brands;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Products;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService catservice;
	
	@Autowired
	private BrandService brandservice;
	
	@GetMapping("/product")
	public String listAll(Model model)
	{
		List<Products> listProducts = productService.listAll();
		
		model.addAttribute("listProducts", listProducts);
		
		return "product/product";
	}
	
	
	@GetMapping("/product/new")
	public String newProduct(Model model)
	{
		Products product = new Products();
		product.setEnabled(true);
		product.setInStock(true);
		List<Category> listCategory = catservice.listAll();
		List<Brands> listBrand = brandservice.ListAll();
		model.addAttribute("product", product);
		model.addAttribute("listCategories", listCategory);
		model.addAttribute("listbrands", listBrand);
		model.addAttribute("pageTitle", "Create New Product");
		
		return "product/product_form";
	}
	
	@PostMapping("/product/save")
	public String savedProduct(Products product, RedirectAttributes redirectAttributes, 
			@RequestParam("fileImage") MultipartFile multipartFile, @RequestParam("extraImage") MultipartFile extramultipartFile,
			@RequestParam("detailNames") String[] detailNames,@RequestParam("detailValues") String[] detailValues) 
					throws IOException  
	{
		System.out.println(product);
		if(!multipartFile.isEmpty() || !extramultipartFile.isEmpty())
		{
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			product.setMainImage(fileName);
			String extrafileName = StringUtils.cleanPath(extramultipartFile.getOriginalFilename());
			product.addExtraImages(extrafileName);
			setProductDetails(detailNames, detailValues, product);
			Products savedProduct = productService.save(product);
			String uploadDir = "product-images/" + savedProduct.getId();
		
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
		else
		{
			
			productService.save(product);
		}
		
		redirectAttributes.addFlashAttribute("message", "The product has been saved successfully.");
		return "redirect:/product";
	}
	
	private void setProductDetails(String[] detailNames, String[] detailValues, Products product) {
		if (detailNames == null || detailNames.length == 0) return;
		
		for (int count = 0; count < detailNames.length; count++) {
			String name = detailNames[count];
			String value = detailValues[count];
			if (!name.isEmpty() && !value.isEmpty()) {
				product.addDetail(name, value);
			}
		}
		
	}
	
	@GetMapping("/product/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable(name = "id") Integer id, @PathVariable(name = "status") boolean enabled,
			RedirectAttributes redirectAttributes)
	{
		productService.updateEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The Product ID " + id + " has been " + status + ".";
		redirectAttributes.addFlashAttribute("message", message);
		
		return "redirect:/product";
	}
	
	@GetMapping("/product/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, Model model)
	{
		try
		{	
			productService.delete(id);
			redirectAttributes.addFlashAttribute("message", "The Product ID " + id + " has deleted successfully");
			return "redirect:/product";
		}
		catch(ProductNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/product";
		}
	}
	
	@GetMapping("/product/edit/{id}")
	public String editProduct(@PathVariable(name = "id") Integer id, Model model,  RedirectAttributes redirectAttributes)
	{
		try 
		{
			Products product = productService.get(id);
			
			model.addAttribute("product", product);
			List<Category> listCategory = catservice.listAll();
			List<Brands> listBrand = brandservice.ListAll();
			model.addAttribute("listCategories", listCategory);
			model.addAttribute("listbrands", listBrand);
			model.addAttribute("pageTitle", "Edit Product {ID : " + id +")");
			
			return "product/product_form";
		}
		catch (ProductNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/product";
		}
	}
	
	@GetMapping("/product/detail/{id}")
	public String viewProductDetails(@PathVariable("id") Integer id, Model model,
			RedirectAttributes ra) {
		try {
			Products product = productService.get(id);			
			model.addAttribute("product", product);		
			
			return "product/product_detail_modal";
			
		} catch (ProductNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			
			return "redirect:/product";
		}
	}	
}
