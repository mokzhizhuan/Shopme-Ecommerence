package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class UserController
{
	@Autowired
	protected UserService service;
	
	@GetMapping("/users")
	public String listAll(Model model)
	{
		List<User> listUser = service.listAll();
		model.addAttribute("listUsers",listUser);
		return "users";
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model)
	{
		User user = new User();
		user.setEnabled(true);
		String title = "Create new User";
		List<Role> listRoles = service.listallrole();
		model.addAttribute("user",user);
		model.addAttribute("listRoles",listRoles);
		model.addAttribute("PageTitle",title);
		return "user_form";
	}
	
	@PostMapping("/save")
	public String savenewUser(User user, RedirectAttributes redirect, int ID)
	{
		System.out.println(user);
		user.setID(user.getID());
		String username = user.getFname() + " " + user.getLname();
		service.save(user);
		redirect.addFlashAttribute("message", "The user (" + username +") has been saved successfully");
		return "redirect:/users";
	}
	
	@GetMapping("users/edit/{ID}")
	public String edituser(@PathVariable(name = "ID") int ID, Model model,
			RedirectAttributes redirectAttributes)
	{
		try
		{
			User user = service.get(ID);
			String title = "Edit User (ID : " + ID + ")";
			List<Role> listRoles = service.listallrole();
			model.addAttribute("user", user);
			model.addAttribute("listRoles",listRoles);
			model.addAttribute("PageTitle",title);
			return "user_form";
		}
		catch(UserNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}
	}
	
	@GetMapping("users/delete/{ID}")
	public String deleteuser(@PathVariable(name = "ID") int ID, Model model,
			RedirectAttributes redirectAttributes)
	{
		try
		{
			service.delete(ID);
			redirectAttributes.addFlashAttribute("message", "The user ID : " + ID +  " is deleted successfully");
			return "redirect:/users";
		}
		catch(UserNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}
	}
}
