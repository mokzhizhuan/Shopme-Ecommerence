package com.shopme.admin.product;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.shopme.common.entity.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>	
{
	public Product findByName(String productname);
	
	@Query("UPDATE Product p SET p.enabled = ?2 WHERE p.ID = ?1")
	@Modifying
	public void updateEnabledStatus(int ID , boolean enabled);
	
	public Long countByID(int ID);
}

