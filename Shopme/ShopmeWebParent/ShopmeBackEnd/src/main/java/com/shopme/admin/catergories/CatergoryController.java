package com.shopme.admin.catergories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.common.entity.Catergory;

@Controller
public class CatergoryController 
{
	@Autowired
	protected CatergoryService service;
	
	@Autowired
	private CatergoryRepository caterrepo;
	
	@GetMapping("/catergories")
	public String listAll(Model model)
	{
		List<Catergory> listCategories = service.listAll();
		model.addAttribute("listCategories",listCategories);
		return "categories/catergories";
	}
	
	@GetMapping("/catergories/new")
	public String newcategories(Model model)
	{
		List<Catergory> listCategories = service.listCategoriesUsedInForm();
		model.addAttribute("catergory",new Catergory());
		String title = "Create new Category";
		model.addAttribute("PageTitle",title);
		model.addAttribute("listCategories", listCategories);
		return "categories/catergories_form";
	}
	
	@PostMapping("/catergories/save")
	public String savenewCategory(Catergory category, RedirectAttributes redirect, int ID)
	{
		System.out.println(category);
		category.setID(category.getID());
		String name = category.getName();
		service.save(category);
		redirect.addFlashAttribute("message", "The category (" + name +") has been saved successfully");
		return "redirect:/catergories";
	}

	@GetMapping("/catergories/edit/{ID}")
	public String edituser(@PathVariable(name = "ID") int ID, Model model,
			RedirectAttributes redirectAttributes)
	{
		try
		{
			Catergory category = service.get(ID);
			String title = "Edit Category (ID : " + ID + ")";
			List<Catergory> listCategories = service.listCategoriesUsedInForm();
			
			model.addAttribute("category", category);
			model.addAttribute("listCategories",listCategories);
			model.addAttribute("PageTitle",title);
			return "categories/edit_catergories_form";
		}
		catch(CategoryNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/catergories";
		}
	}
	
	@GetMapping("/catergories/delete/{ID}")
	public String deletecategories(@PathVariable(name = "ID") int ID, Model model,
			RedirectAttributes redirectAttributes)
	{
		try
		{
			service.delete(ID);
			redirectAttributes.addFlashAttribute("message", "The Catergory ID : " + ID +  " is deleted successfully");
			return "redirect:/catergories";
		}
		catch(CategoryNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/catergories";
		}
	}
}
