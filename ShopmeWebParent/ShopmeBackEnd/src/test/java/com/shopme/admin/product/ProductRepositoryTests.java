package com.shopme.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Category;
import com.shopme.common.entity.Products;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

	@Autowired
	private ProductRepo repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateProduct() {
		Category category = entityManager.find(Category.class, 5);
		
		Products product = new Products();
		product.setName("Acer Aspire Desktop");
		product.setAilas("acer_aspire_desktop");
		product.setShortDes("Short description for Acer Aspire");
		product.setDescription("Full description for Acer Aspire");
		
		product.setCategory(category);
		
		product.setDiscountcost(678);
		product.setPrice(600);
		product.setEnabled(true);
		product.setInStock(true);
		
		product.setCreatedTIme(new Date());
		product.setUpdatedTime(new Date());
		
		Products savedProduct = repo.save(product);
		
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllProducts() {
		Iterable<Products> iterableProducts = repo.findAll();
		
		iterableProducts.forEach(System.out::println);
	}
	
	@Test
	public void testGetProduct() {
		Integer id = 2;
		Products product = repo.findById(id).get();
		System.out.println(product);
		
		assertThat(product).isNotNull();
	}
	
	@Test
	public void testUpdateProduct() {
		Integer id = 1;
		Products product = repo.findById(id).get();
		product.setPrice(499);
		
		repo.save(product);
		
		Products updatedProduct = entityManager.find(Products.class, id);
		
		assertThat(updatedProduct.getPrice()).isEqualTo(499);
	}
	
	/*@Test
	public void testDeleteProduct() {
		Integer id = 3;
		repo.deleteById(id);
		
		Optional<Products> result = repo.findById(id);
		
		assertThat(!result.isPresent());
	}*/
	
	@Test
	public void testSaveProductWithImages() {
		Integer productId = 1;
		Products product = repo.findById(productId).get();
		
		product.setMainImage("main image.jpg");
		product.addExtraImages("extra image 1.png");
		product.addExtraImages("extra_image_2.png");
		product.addExtraImages("extra-image3.png");
		
		Products savedProduct = repo.save(product);
		
		assertThat(savedProduct.getImages().size()).isEqualTo(3);
	}
	
	@Test
	public void testSaveProductWithDetails() {
		Integer productId = 1;
		Products product = repo.findById(productId).get();
		
		product.addDetail("Device Memory", "128 GB");
		product.addDetail("CPU Model", "MediaTek");
		product.addDetail("OS", "Android 10");
		
		Products savedProduct = repo.save(product);
		assertThat(savedProduct.getDetails()).isNotEmpty();		
	}
	
	/*@Test
	public void testUpdateReviewCountAndAverageRating() {
		Integer productId = 100;
		repo.updateReviewCountAndAverageRating(productId);
	}*/
}
