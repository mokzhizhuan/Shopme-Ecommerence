package com.shopme.admin.product;

import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.Products;

public interface ProductRepo extends CrudRepository<Products, Integer> {

}
