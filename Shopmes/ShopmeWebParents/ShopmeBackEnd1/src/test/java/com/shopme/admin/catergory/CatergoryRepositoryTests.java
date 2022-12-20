package com.shopme.admin.catergory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.catergories.CategoryRepository;
import com.shopme.common.entity.Category;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CatergoryRepositoryTests 
{
	@Autowired
	private CategoryRepository caterrepo;
	
	@Test
	public void testCatergoryCreateRootCatergory()
	{
		Category catergory = new Category("Electronics");
		Category savedCatergory = caterrepo.save(catergory);
		
		assertThat(savedCatergory.getID()).isGreaterThan(0);
	}
	
	@Test
	public void testCatergoryCreateSubCatergory()
	{
		Category parent = new Category(1);
		Category laptops = new Category("Laptops" , parent);
		Category componments = new Category("Computer Components" , parent);
		
		caterrepo.saveAll(List.of(laptops, componments));
	}
	
	@Test
	public void testGetCatergory()
	{
		Category catergory = caterrepo.findById(1).get();
		
		System.out.println(catergory.getName());
		
		Set<Category> childcater = catergory.getChildcater();
		
		for (Category subCatergory : childcater)
		{
			System.out.println(subCatergory.getName());
		}
		
		assertThat(childcater.size()).isGreaterThan(0);
	}
	
	@Test
	public void testListRootCategory()
	{
		List<Category> rootCatergories = caterrepo.findRootCatergories();
		rootCatergories.forEach(cat -> System.out.println(cat.getName()));
	}
}
