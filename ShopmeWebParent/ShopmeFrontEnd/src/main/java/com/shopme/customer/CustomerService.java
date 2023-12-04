package com.shopme.customer;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.setting.CountryRepository;

import com.shopme.common.entity.AuthenticationType;

@Service
public class CustomerService {

	@Autowired
	private CountryRepository countryrepo;
	
	@Autowired
	private CustomerRepository customerrepo;
	
	public List<Country> listAllCountries()
	{
		return countryrepo.findAllByOrderByNameAsc();		
	}

	public boolean isEmailUnique(String email) {
		Customer customer = customerrepo.findByEmail(email);
		return customer == null;
	}
	
	public void registerCustomer(Customer customer) 
	{
		customer.setEnabled(false);
		customer.setCreatedTIme(new Date());
		customer.setAuthenticationType(AuthenticationType.DATABASE);
		
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String randomCode = salt.toString();
		customer.setVerficationCode(randomCode);
		
		customerrepo.save(customer);
		
	}
	
	public Customer getCustomerByEmail(String email) {
		return customerrepo.findByEmail(email);
	}
	
	
	public boolean verify(String verificationCode) {
		Customer customer = customerrepo.findByVerficationCode(verificationCode);
		
		if (customer == null || customer.isEnabled()) {
			return false;
		} else {
			customerrepo.enable(customer.getId());
			return true;
		}
	}
	
	public void updateAuthenticationType(Customer customer, AuthenticationType type) {
		if (!customer.getAuthenticationType().equals(type)) 
		{
			customerrepo.updateAuthenticationType(customer.getId(), type);
		}
	}
	
	public void addNewCustomerUponOAuthLogin(String name, String email, String countryCode,
			AuthenticationType authenticationType) {
		Customer customer = new Customer();
		customer.setEmail(email);
		setName(name, customer);
		
		customer.setEnabled(true);
		customer.setCreatedTIme(new Date());
		customer.setAuthenticationType(authenticationType);
		customer.setPassword("");
		customer.setAddress("");
		customer.setCity("");
		customer.setState("");
		customer.setPhoneNumber("");
		customer.setPostalCode("");
		customer.setCountry(countryrepo.findByCode(countryCode));
		
		customerrepo.save(customer);
	}	
	
	private void setName(String name, Customer customer) {
		String[] nameArray = name.split(" ");
		if (nameArray.length < 2) {
			customer.setFirstName(name);
			customer.setLastName("");
		} else {
			String firstName = nameArray[0];
			customer.setFirstName(firstName);
			
			String lastName = name.replaceFirst(firstName + " ", "");
			customer.setLastName(lastName);
		}
	}
	
	public void update(Customer customerInForm) {
		Customer customerInDB = customerrepo.findById(customerInForm.getId()).get();
		
		if (customerInDB.getAuthenticationType().equals(AuthenticationType.DATABASE)) {
				customerInForm.setPassword(customerInDB.getPassword());		
		} else {
			customerInForm.setPassword(customerInDB.getPassword());
		}
		
		customerInForm.setEnabled(customerInDB.isEnabled());
		customerInForm.setCreatedTIme(customerInDB.getCreatedTIme());
		customerInForm.setVerficationCode(customerInDB.getVerficationCode());
		customerInForm.setAuthenticationType(customerInDB.getAuthenticationType());
		customerInForm.setResetPasswordToken(customerInDB.getResetPasswordToken());
		
		customerrepo.save(customerInForm);
	}

	public String updateResetPasswordToken(String email) throws CustomerNotFoundException {
		Customer customer = customerrepo.findByEmail(email);
		if (customer != null) {
			String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 30) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
			String token = salt.toString();
			customer.setResetPasswordToken(token);
			customerrepo.save(customer);
			
			return token;
		} else {
			throw new CustomerNotFoundException("Could not find any customer with the email " + email);
		}
	}	
	
	public Customer getByResetPasswordToken(String token) 
	{
		return customerrepo.findByResetPasswordToken(token);
	}
	
	public void updatePassword(String token, String newPassword) throws CustomerNotFoundException {
		Customer customer = customerrepo.findByResetPasswordToken(token);
		if (customer == null) {
			throw new CustomerNotFoundException("No customer found: invalid token");
		}
		
		customer.setPassword(newPassword);
		customer.setResetPasswordToken(null);
		
		customerrepo.save(customer);
	}
}
