package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests 
{
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser()
	{
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userMZZ = new User("gamerdark44@outlook.com", "Mok" , "Zhi Zhuan", "adsj532$32%32&t");
		userMZZ.addRole(roleAdmin);
		
		User saveduser = repo.save(userMZZ);
		assertThat(saveduser.getID()).isGreaterThan(0);
	}
	
	@Test
	public void testCreaterestUser()
	{
		Role roleeditor = entityManager.find(Role.class, 3);
		User userTan = new User("spiraldark44@outlook.com", "Tan" , "Hock Seng", "d5321d6");
		userTan.addRole(roleeditor);
		
		User saveduser = repo.save(userTan);
		assertThat(saveduser.getID()).isGreaterThan(0);
		
		Role roleassistant = entityManager.find(Role.class, 5);
		User userJohn = new User("spiralsdark44@outlook.com", "John" , "Pulver", "t5321d6");
		userTan.addRole(roleassistant);
		
		User savedusers = repo.save(userJohn);
		assertThat(savedusers.getID()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers()
	{
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserByID()
	{
		User userMZZ = repo.findById(2).get();
		System.out.println(userMZZ);
		assertThat(userMZZ).isNotNull();
	}
	
	@Test
	public void testGetUserByEmail()
	{
		String email = "spiraldark44@outlook.com";
		User user = repo.getUserbyEmail(email);
		
		assertThat(user).isNotNull();
	}
}
