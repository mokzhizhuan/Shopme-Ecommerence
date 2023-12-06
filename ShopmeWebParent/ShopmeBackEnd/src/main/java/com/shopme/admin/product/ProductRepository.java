package com.shopme.admin.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.util.Streamable;

import com.shopme.common.entity.Products;

public interface ProductRepository extends PagingAndSortingRepository <Products, Integer> {
	
	public Long countById(Integer id);
	
	public Products findByName(String name);

	@Query("SELECT p FROM Products p WHERE p.name LIKE %?1%")
	public Page<Products> searchProductsByName(String keyword, Pageable pageable);
	
	@Query("UPDATE Products p SET p.enabled = ?2 WHERE p.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);

	public Products save(Products product);

	public void deleteById(Integer id);

	@Query("SELECT p FROM Products p WHERE p.id = ?1")
	public Products findById(Integer id);

	public Iterable<Products> findAll();
	
}
