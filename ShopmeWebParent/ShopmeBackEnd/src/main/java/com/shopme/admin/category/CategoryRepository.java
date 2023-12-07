package com.shopme.admin.category;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entity.Category;

public interface CategoryRepository extends CrudRepository <Category, Integer> {
	
	public Long countById(int id);
	
	@Query("SELECT c FROM Category c WHERE c.name = :name")
	public Category findByName(String name);
	
	@Query("SELECT c FROM Category c WHERE c.alias = :alias")
	public Category findByAlias(String alias);
	
	@Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1")
	@Modifying
	public void updateEnabledStatus(int id, boolean enabled);

	public Category getCategoryByName(@Param("name") String name);
}
