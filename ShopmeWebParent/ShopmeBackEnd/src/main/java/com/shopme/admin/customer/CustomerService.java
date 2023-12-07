package com.shopme.admin.customer;

import java.util.List;
import java.util.NoSuchElementException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.setting.country.CountryRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {
	public static final int CUSTOMERS_PER_PAGE = 10;
	
	@Autowired 
	private CustomerRepository customerRepo;
	
	@Autowired 
	private CustomerRepo customerRepos;
	
	@Autowired 
	private CountryRepository countryRepo;	
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, CUSTOMERS_PER_PAGE, customerRepo);
	}
	
	public void updateCustomerEnabledStatus(Integer id, boolean enabled) {
		customerRepo.updateEnabledStatus(id, enabled);
	}
	
	public Customer get(Integer id) throws CustomerNotFoundException {
		try {
			return customerRepos.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new CustomerNotFoundException("Could not find any customers with ID " + id);
		}
	}

	public List<Country> listAllCountries() {
		return countryRepo.findAllByOrderByNameAsc();
	}		
	
	public boolean isEmailUnique(Integer id, String email) {
		Customer existCustomer = customerRepo.findByEmail(email);

		if (existCustomer != null && existCustomer.getId() != id) {
			// found another customer having the same email
			return false;
		}
		
		return true;
	}
	
	public void save(Customer customerInForm) {
		Customer customerInDB = customerRepos.findById(customerInForm.getId()).get();
		
		if (!customerInForm.getPassword().isEmpty()) {
			String encodedPassword = passwordEncoder.encode(customerInForm.getPassword());
			customerInForm.setPassword(encodedPassword);			
		} else {
			customerInForm.setPassword(customerInDB.getPassword());
		}		
		
		customerInForm.setEnabled(customerInDB.isEnabled());
		customerInForm.setCreatedTIme(customerInDB.getCreatedTIme());
		customerInForm.setVerficationCode(customerInDB.getVerficationCode());
		customerInForm.setAuthenticationType(customerInDB.getAuthenticationType());
		customerInForm.setResetPasswordToken(customerInDB.getResetPasswordToken());
		
		customerRepo.save(customerInForm);
	}
	
	public void delete(Integer id) throws CustomerNotFoundException {
		Long count = customerRepo.countById(id);
		if (count == null || count == 0) {
			throw new CustomerNotFoundException("Could not find any customers with ID " + id);
		}
		
		customerRepo.deleteById(id);
	}
	
}
