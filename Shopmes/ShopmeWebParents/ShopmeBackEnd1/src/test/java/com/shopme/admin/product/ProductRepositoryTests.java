package com.shopme.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests 
{
	@Autowired
	private ProductRepository ProductRepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateProduct()
	{
		Category category = entityManager.find(Category.class, 1);
		
		Product product = new Product();
		
		product.setName("Dell Laptop");
		
		product.setCategory(category);
		
		product.setCost(852.0f);
		product.setEnabled(true);
		product.setInStock(true);
		
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());
		
		Product savedProduct = ProductRepo.save(product);
		
		assertThat(savedProduct).isNotNull();
		
		assertThat(savedProduct.getID()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllProducts()
	{
		Iterable<Product> iterableProduct = ProductRepo.findAll();
		
		iterableProduct.forEach(System.out::println);
	}
}
