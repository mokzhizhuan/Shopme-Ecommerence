package com.shopme.admin.brands;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.shopme.common.entity.Brands;

public interface BrandRepository extends CrudRepository <Brands, Integer> {
	
	public Long countById(Integer id);
	
	public Brands findByName(String name);
	
	@Query("SELECT b FROM Brands b")
	public List<Brands> findAlls();
	
}
