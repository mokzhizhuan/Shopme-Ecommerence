package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests 
{
	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateAdminRole()
	{
		Role RoleAdmin = new Role("Admin", "manages everything");
		Role savedRole = repo.save(RoleAdmin);
		assertThat(savedRole.getID()).isGreaterThan(0);
		
	}
	
	@Test
	public void testCreateResrtRole()
	{
		Role RoleSalesperson = new Role("Salesperson", "manages products price , customers , shipping "
				+ "orders and sales reports");
		Role RoleEditor = new Role("Editor", "manages catergories, brands, "
				+ "products, articles and menus");
		Role RoleShipper = new Role("Shipper", "view products, view orders and "
				+ "update order status");
		Role RoleAssistant = new Role("Assistant", "manage questions and reviews");
		
		repo.saveAll(List.of(RoleSalesperson, RoleEditor , RoleShipper, RoleAssistant));
	}
}
