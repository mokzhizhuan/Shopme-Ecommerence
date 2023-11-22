package com.shopme.admin.brands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandRestController {
	
	@Autowired
	private BrandService service;
	
	@PostMapping("/brand/check_name")
	public String checkDuplicateEmail(@Param("name") String name, @Param("id") int id)
	{
		return service.isNameUnique(id ,name) ? "OK" : "Duplicated";
	}
}
