package com.shopme.customer;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.Customer;

import com.shopme.common.entity.AuthenticationType;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
	
	@Query("SELECT c FROM Customer c WHERE c.email = ?1")
	public Customer findByEmail(String email);

	@Query("SELECT c FROM Customer c WHERE c.VerficationCode = ?1")
	public Customer findByVerficationCode(String code);
	
	@Query("UPDATE Customer c SET c.enabled = true, c.VerficationCode = null WHERE c.id = ?1")
	@Modifying
	public void enable(Integer id);

	@Query("UPDATE Customer c SET c.authenticationType = ?2  WHERE c.id = ?1")
	@Modifying
	public void updateAuthenticationType(Integer customerId, AuthenticationType type);
	
	public Customer findByResetPasswordToken(String token);
}
