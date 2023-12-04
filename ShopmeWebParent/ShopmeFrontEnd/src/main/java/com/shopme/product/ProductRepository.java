package com.shopme.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.shopme.common.entity.Products;


public interface ProductRepository extends PagingAndSortingRepository  <Products, Integer> {

	@Query("SELECT p FROM Products p WHERE p.enabled = true "
			+ "AND (p.category.id = ?1 OR p.category.allParentIDs LIKE %?2%)"
			+ " ORDER BY p.name ASC")
	public Page<Products> listByCategory(Integer categoryId, String categoryIDMatch, Pageable pageable);
	
	
	public Products findByAilas(String ailas);


	@Query(value = "SELECT * FROM Products WHERE enabled = true AND "
			+ "MATCH(name, short_description, full_description) AGAINST (?1)", 
			nativeQuery = true)
	public Page<Products> search(String keyword, Pageable pageable);


	public List<Products> findAll();

	
	/*@Query("Update Products p SET p.averageRating = COALESCE((SELECT AVG(r.rating) FROM Review r WHERE r.product.id = ?1), 0),"
			+ " p.reviewCount = (SELECT COUNT(r.id) FROM Review r WHERE r.product.id =?1) "
			+ "WHERE p.id = ?1")
	@Modifying
	public void updateReviewCountAndAverageRating(Integer productId);*/


}
