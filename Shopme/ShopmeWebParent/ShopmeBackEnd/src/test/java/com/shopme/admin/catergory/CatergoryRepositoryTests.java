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

import com.shopme.admin.catergories.CatergoryRepository;
import com.shopme.common.entity.Catergory;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CatergoryRepositoryTests 
{
	@Autowired
	private CatergoryRepository caterrepo;
	
	@Test
	public void testCatergoryCreateRootCatergory()
	{
		Catergory catergory = new Catergory("Electronics");
		Catergory savedCatergory = caterrepo.save(catergory);
		
		assertThat(savedCatergory.getID()).isGreaterThan(0);
	}
	
	@Test
	public void testCatergoryCreateSubCatergory()
	{
		Catergory parent = new Catergory(1);
		Catergory laptops = new Catergory("Laptops" , parent);
		Catergory componments = new Catergory("Computer Components" , parent);
		
		caterrepo.saveAll(List.of(laptops, componments));
	}
	
	@Test
	public void testGetCatergory()
	{
		Catergory catergory = caterrepo.findById(1).get();
		
		System.out.println(catergory.getName());
		
		Set<Catergory> childcater = catergory.getChildcater();
		
		for (Catergory subCatergory : childcater)
		{
			System.out.println(subCatergory.getName());
		}
		
		assertThat(childcater.size()).isGreaterThan(0);
	}
	
}
