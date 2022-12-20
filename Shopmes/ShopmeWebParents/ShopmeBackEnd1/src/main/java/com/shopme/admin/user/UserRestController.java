package com.shopme.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController 
{
	@Autowired
	protected UserService service;
	
	@PostMapping("/checkemail")
	public String checkDuplicateEmail(@Param("email") String email)
	{
		return service.isEmailUnique(email) ? "OK" : "Duplicated";
	}
	
}
