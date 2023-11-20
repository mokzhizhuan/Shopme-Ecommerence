package com.shopme.admin.brands;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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
import com.shopme.admin.category.CategoryService;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.common.entity.Brands;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class BrandController {

	@Autowired
	private BrandService service;
	
	@Autowired
	private CategoryService catservice;
	
	@GetMapping("/brand")
	public String listAll(Model model)
	{
		List<Brands> listbrands = service.ListAll();
		model.addAttribute("listBrands", listbrands);
		
		return "brand";
	}
	
	@GetMapping("/brand/new")
	public String newUser(Model model)
	{
		List<Category> listCategory = catservice.listAll();
		
		model.addAttribute("listCategories", listCategory);
		Brands brand = new Brands();
		model.addAttribute("brand", brand);
		model.addAttribute("pageTitle", "Create New Brand");
		return "brand_form";
	}
	
	@PostMapping("/brand/save")
	public String savedBrand(Brands brand, RedirectAttributes redirectAttributes, @RequestParam("fileimage") MultipartFile multipartFile)
			throws IOException  
	{
		System.out.println(brand);
		System.out.println(multipartFile.getOriginalFilename());
		
		if(!multipartFile.isEmpty())
		{
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			brand.setLogo(fileName);
			Brands savedbrand = service.save(brand);
			String uploadDir = "brands-logos/" + savedbrand.getId();
		
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
		else
		{
			service.save(brand);
		}
		
		redirectAttributes.addFlashAttribute("message", "The brand has been saved successfully.");
		return "redirect:/brand";
	}
	
	@GetMapping("/brand/edit/{id}")
	public String editBrand(@PathVariable(name = "id") Integer id, Model model,  RedirectAttributes redirectAttributes) 
	{
		try
		{	
			Brands brand = service.get(id);
			List<Category> listCategory = catservice.listAll();
			model.addAttribute("brand", brand);
			model.addAttribute("pageTitle", "Edit User {ID : " + id +")");
			model.addAttribute("listCategories", listCategory );
			return "brand_form";
		}
		catch(BrandNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/brand";
		}
	}
	
	@GetMapping("/brand/delete/{id}")
	public String deleteBrand(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) 
	{
		try
		{	
			service.delete(id);
			String brandDir = "/brand-logos/" + id;
			redirectAttributes.addFlashAttribute("message", "The brand ID " + id + " has deleted successfully");
			return "redirect:/brand";
		}
		catch(BrandNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/brand";
		}
	}
}
