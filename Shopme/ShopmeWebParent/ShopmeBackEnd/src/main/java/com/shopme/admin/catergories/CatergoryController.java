package com.shopme.admin.catergories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String savenewCategory(Catergory category, RedirectAttributes redirect)
	{
		Catergory savedcategory = service.save(category);
		return "redirect:/catergories";
	}

}
