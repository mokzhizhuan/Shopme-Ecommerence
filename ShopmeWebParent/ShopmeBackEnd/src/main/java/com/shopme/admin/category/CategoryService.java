package com.shopme.admin.category;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.user.UserNotFoundException;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository caterrepo;
	
	public List<Category> listAll()
	{
		return (List<Category>)caterrepo.findAll();
	}

	public Category save(Category category) {
		// TODO Auto-generated method stub
		return caterrepo.save(category);
	}
	
	public Category get(int id) throws CategoryNotFoundException {
		try {
		return caterrepo.findById(id).get();
		}
		catch(NoSuchElementException ex)
		{
			throw new CategoryNotFoundException("Could not find any user with ID " + id);
		}
	}
	
	public void delete(Integer id) throws CategoryNotFoundException
	{
		Long countbyID = caterrepo.countById(id);
		if(countbyID == null || countbyID == 0)
		{
			throw new CategoryNotFoundException("Could not find any category with ID " + id);
		}
		
		caterrepo.deleteById(id);
	}
	
	public void updateEnabledStatus(int id , boolean enabled)
	{
		caterrepo.updateEnabledStatus(id, enabled);
	}

	public boolean isNameUnique(int id, String name, String alias) 
	{
		Category categorybyName = caterrepo.getCategoryByName(name);
		
		if (categorybyName == null) return true;
		
		boolean isCreatingNew = (id == 0);
		
		if(isCreatingNew)
		{
			if(categorybyName != null) 
			{
				return false;
			}
			else
			{
				if(categorybyName.getId() != id)
				{
					return false;
				}
			}
		}
		
		return true;
	}
}
