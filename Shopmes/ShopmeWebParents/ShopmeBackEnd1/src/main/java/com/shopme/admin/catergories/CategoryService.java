package com.shopme.admin.catergories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CategoryService 
{
	@Autowired
	private CategoryRepository caterrepo;
	
	public List<Category> listAll()
	{
		List<Category> RootCategories = caterrepo.findRootCatergories();
		return ListHierarchicalCategories(RootCategories);
	}
	
	private List<Category> ListHierarchicalCategories(List<Category> RootCategories)
	{
		List<Category> hierarchicalCategories = new ArrayList<>();
		
		for (Category rootCategory : RootCategories)
		{
			hierarchicalCategories.add(Category.copyFull(rootCategory));
			
			Set<Category> children = rootCategory.getChildcater();
			
			for (Category subCategory : children)
			{
				String name = "--" + subCategory.getName();
				hierarchicalCategories.add(Category.copyFull(subCategory, name));
				listSubHierarchicalCategories(hierarchicalCategories, subCategory , 1);
			}
		}
		
		return hierarchicalCategories;
	}

	private void listSubHierarchicalCategories(List<Category> hierarchicalCategories, 
			Category parent, int sublevel)
	{
		Set<Category> children = parent.getChildcater();
		int newSublevel = sublevel + 1;
		
		for (Category subCategory : children)
		{
			String name = "";
			for(int i = 0; i < newSublevel; i++)
			{
				name += "--";
			}
			name += subCategory.getName();
			
			hierarchicalCategories.add(Category.copyFull(subCategory, name));
			
			listSubHierarchicalCategories(hierarchicalCategories, subCategory , newSublevel);
		}
	}
	
	public Category save(Category category) 
	{
		String idtest = Integer.toString(category.getID());
		boolean update = (idtest != null);
		if(update && category.getID() > 0)
		{
			return caterrepo.save(category);	
		}
		return caterrepo.save(category);
	}
	
	public List<Category> listCategoriesUsedInForm() 
	{
		List<Category> categoriesInUsedForm = new ArrayList<>();
		Iterable<Category> categoriesinDB = caterrepo.findAll(); 
		
		for (Category category : categoriesinDB)
		{
			if(category.getParent() == null)
			{
				System.out.println(category.getName());
				
				Set <Category> children = category.getChildcater();
				
				for (Category subCategory : children)
				{
					String name = "--" + subCategory.getName();
					categoriesInUsedForm.add(new Category(name));
					listSubCategoriesInForm(categoriesInUsedForm, subCategory, 1);
				}
			}
		}
		
		return categoriesInUsedForm;
	}

	private void listSubCategoriesInForm(List<Category> categoriesInUsedForm , Category parent, int sublevel) 
	{
		int newsublevel = sublevel + 1;
		Set <Category> children = parent.getChildcater();
		for (Category subCategory : children)
		{
			String name = "";
			for(int i = 0 ; i < newsublevel; i++)
			{
				name += "--";
			}
			categoriesInUsedForm.add(new Category(name));
			
			listSubCategoriesInForm(categoriesInUsedForm , subCategory, newsublevel);
		}
	}
	
	public Category get(int ID) throws CategoryNotFoundException
	{
		try
		{
			return caterrepo.findById(ID).get();
		}
		catch(NoSuchElementException ex)
		{
			throw new CategoryNotFoundException("Could not find any Category with ID" + ID);
		}
	}

	public String checkUnique(String categoryname) 
	{
				
		Category categoryBYName = caterrepo.findByName(categoryname);
		if (categoryBYName != null)
		{
			return "DuplicatedName";
		}
		
		return "OK";
	}
	
	public void delete(int ID) throws CategoryNotFoundException
	{
		Long countID = caterrepo.countByID(ID);
		if(countID == null || countID == 0)
		{
			throw new CategoryNotFoundException("Category is not found. Category ID : " + ID);
		}
		else
		{
			caterrepo.deleteById(ID);
		}
	}
}
