package com.shopme.admin.brands;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entity.Brands;

public interface BrandRepository extends CrudRepository <Brands, Integer> {
	
	public Long countById(Integer id);
	
	@Query("SELECT b FROM Brands b WHERE b.name = :name")
	public Brands findByName(String name);
	
	@Query("SELECT b FROM Brands b")
	public List<Brands> findAlls();
	
	public Brands getBrandByName(@Param("name") String name);
}
