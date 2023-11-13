package com.shopme.admin.product;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entity.Brands;
import com.shopme.common.entity.Products;

public interface ProductRepository extends CrudRepository <Products, Integer> {
	
	public Long countById(Integer id);
	
	public Products findByName(String name);

	@Query("UPDATE Products p SET p.enabled = ?2 WHERE p.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
	
}
