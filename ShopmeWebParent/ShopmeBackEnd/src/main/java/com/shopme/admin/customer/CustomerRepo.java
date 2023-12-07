package com.shopme.admin.customer;

import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.Customer;

public interface CustomerRepo extends CrudRepository<Customer, Integer> {

}
