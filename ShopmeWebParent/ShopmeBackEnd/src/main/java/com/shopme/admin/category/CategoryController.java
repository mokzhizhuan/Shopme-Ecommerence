package com.shopme.admin.category;

import java.io.IOException;
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
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@GetMapping("/categories")
	public String listAll(Model model)
	{
		List<Category> listCategory = service.listAll();
		model.addAttribute("listCategories", listCategory);
		
		return "categories";
	}
	
	@GetMapping("/categories/new")
	public String newCategory(Model model)
	{
		List<Category> listCategory = service.listAll();
		model.addAttribute("category", new Category());
		model.addAttribute("listCategories", listCategory);
		model.addAttribute("pageTitle", "Create New Categories");
		
		return "categories_form";
	}
	
	@PostMapping("/categories/save")
	public String savedUser(Category category, RedirectAttributes redirectAttributes, @RequestParam("fileimage") MultipartFile multipartFile) throws IOException  
	{
		System.out.println(category);
		System.out.println(multipartFile.getOriginalFilename());
		
		if(!multipartFile.isEmpty())
		{
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			category.setImage(fileName);
			Category savedcategory = service.save(category);
			String uploadDir = "category-images/" + savedcategory.getId();
		
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
		
		service.save(category);
		
		redirectAttributes.addFlashAttribute("message", "The category has been saved successfully.");
		return "redirect:/categories";
	}
	
	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable(name = "id") int id, Model model,  RedirectAttributes redirectAttributes) 
	{
		try
		{	
			List<Category> listCategory = service.listAll();
			List<Category> listCategoryedit = service.listAll();
			Category category = service.get(id);
			String parentname = category.getParent().getName();
			for(Category categories : listCategory)
			{
				if(category.getName() == parentname)
				{
					listCategoryedit.remove(categories);
				}
			}
			model.addAttribute("category", category);
			model.addAttribute("pageTitle", "Edit Category {ID : " + id +")");
			model.addAttribute("listCategories", listCategory);
			model.addAttribute("parentname", parentname);
			return "categories_form";
		}
		catch(CategoryNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/categories";
		}
	}
	
	@GetMapping("/categories/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes)
	{
		try
		{	
			List<Category> listCategory = service.listAll();
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "The Category ID " + id + " has deleted successfully");
			return "redirect:/categories";
		}
		catch(CategoryNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/categories";
		}
	}
	
	@GetMapping("/categories/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable(name = "id") Integer id, @PathVariable(name = "status") boolean enabled,
			RedirectAttributes redirectAttributes)
	{
		service.updateEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The Category ID " + id + " has been " + status + ".";
		redirectAttributes.addFlashAttribute("message", message);
		
		return "redirect:/categories";
	}
}
