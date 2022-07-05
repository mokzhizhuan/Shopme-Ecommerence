package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
public class UserService 
{
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private RoleRepository rolerepo;
	
	@Autowired
	private PasswordEncoder passwordencoder;
	
	public List<User> listAll()
	{
		return(List<User>) userrepo.findAll();
	}
	
	public List<Role> listallrole()
	{
		return (List<Role>) rolerepo.findAll();
	}

	public void save(User user) 
	{
		String idtest = Integer.toString(user.getID());
		boolean update = (idtest != null);
		if(update && user.getID() > 0)
		{
			User existuser = userrepo.findById(user.getID()).get();
			
			if (user.getPassword().isEmpty())
			{
				user.setPassword(existuser.getPassword());
			}
			else
			{
				encodePassword(user);
			}
		}
		else
		{
			encodePassword(user);
		}
		userrepo.save(user);
	}
	
	private void encodePassword(User user)
	{
		String encodedpassword = passwordencoder.encode(user.getPassword());
		user.setPassword(encodedpassword);
	}

	public boolean isEmailUnique(String email) 
	{
		User user = userrepo.getUserbyEmail(email);
		
		if (user == null)
		{
			return true;
		}
		
		return user == null;
	}

	public User get(int ID) throws UserNotFoundException 
	{
		try
		{
			return userrepo.findById(ID).get();
		}
		catch(NoSuchElementException ex)
		{
			throw new UserNotFoundException("Could not find any user ID" + ID);
		}
	}
	
	public void delete(int ID) throws UserNotFoundException
	{
		Long countID = userrepo.countByID(ID);
		if(countID == null || countID == 0)
		{
			throw new UserNotFoundException("User is not found. User ID : " + ID);
		}
		else
		{
			userrepo.deleteById(ID);
		}
	}
}
