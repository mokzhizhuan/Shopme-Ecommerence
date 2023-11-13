package com.shopme.admin.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryRestController {
	
	@Autowired
	private CategoryService service;
	
	@PostMapping("/users/check_name")
	public String checkDuplicateEmail(@Param("name") String name,@Param("alias") String alias , @Param("id") int id)
	{
		return service.isNameUnique(id ,name, alias) ? "OK" : "Duplicated";
	}
}
