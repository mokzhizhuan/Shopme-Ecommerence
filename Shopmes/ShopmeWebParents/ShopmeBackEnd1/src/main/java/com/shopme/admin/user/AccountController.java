package com.shopme.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.common.entity.User;

@Controller
public class AccountController 
{
	@Autowired
	protected UserService service;
	
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal ShopmeUserDetails loggedUser, Model model)
	{
		String email = loggedUser.getUsername();
		User user = service.getbyEmail(email);
		model.addAttribute("user" , user);
		
		return "users/account_form";
		
	}
}