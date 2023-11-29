package com.shopme.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService custservice;
	
	@GetMapping("/register")
	public String showcustomerregistrationForm(Model model)
	{
		List<Country> countrylist = custservice.listAllCountries();
		
		model.addAttribute("listCountries", countrylist);
		model.addAttribute("pageTitle" , "Customer Registration" );
		model.addAttribute("customer" , new Customer());
		
		return "register_form";
	}
}
