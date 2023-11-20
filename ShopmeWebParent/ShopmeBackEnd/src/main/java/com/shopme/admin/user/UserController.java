package com.shopme.admin.user;


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
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepository userrepo;
	
	@GetMapping("/users")
	public String listAll(Model model)
	{
		List<User> listUsers = service.ListAll();
		model.addAttribute("listUsers", listUsers);
		
		return "users";
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model)
	{
		List<Role> listRoles = service.listRoles();
		
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New User");
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String savedUser(User user, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile) 
			throws IOException
	{
		System.out.println(user);
		System.out.println(multipartFile.getOriginalFilename());
		
		if(user.getPassword().isEmpty())
		{
			User existuser = userrepo.getUserByEmail(user.getEmail());
			user.setPassword(existuser.getPassword());
		}
		
		if(user.getPhotos() == null)
		{
			User existuser = userrepo.getUserByEmail(user.getEmail());
			user.setPhotos(existuser.getPhotos());
		}
		
		if(!multipartFile.isEmpty())
		{
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = service.save(user);
			String uploadDir = "user-photos/" + savedUser.getId();
		
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
		
		service.save(user);
		
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, Model model,  RedirectAttributes redirectAttributes) 
	{
		try
		{	
			List<Role> listRoles = service.listRoles();
			User user = service.get(id);
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Edit User {ID : " + id +")");
			model.addAttribute("listRoles", listRoles);
			return "user_form";
		}
		catch(UserNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}
	}
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) 
	{
		try
		{	
			List<Role> listRoles = service.listRoles();
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "The user ID " + id + " has deleted successfully");
			return "redirect:/users";
		}
		catch(UserNotFoundException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}
	}
	
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable(name = "id") Integer id, @PathVariable(name = "status") boolean enabled,
			RedirectAttributes redirectAttributes)
	{
		service.updateEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The user ID " + id + " has been " + status + ".";
		redirectAttributes.addFlashAttribute("message", message);
		
		return "redirect:/users";
	}
}
