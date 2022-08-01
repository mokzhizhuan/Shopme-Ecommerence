package com.shopme.admin.catergories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Catergory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CatergoryService 
{
	@Autowired
	private CatergoryRepository caterrepo;
	
	public List<Catergory> listAll()
	{
		List<Catergory> RootCategories = caterrepo.findRootCatergories();
		return ListHierarchicalCategories(RootCategories);
	}
	
	private List<Catergory> ListHierarchicalCategories(List<Catergory> RootCategories)
	{
		List<Catergory> hierarchicalCategories = new ArrayList<>();
		
		for (Catergory rootCategory : RootCategories)
		{
			hierarchicalCategories.add(Catergory.copyFull(rootCategory));
			
			Set<Catergory> children = rootCategory.getChildcater();
			
			for (Catergory subCategory : children)
			{
				String name = "--" + subCategory.getName();
				hierarchicalCategories.add(Catergory.copyFull(subCategory, name));
				listSubHierarchicalCategories(hierarchicalCategories, subCategory , 1);
			}
		}
		
		return hierarchicalCategories;
	}

	private void listSubHierarchicalCategories(List<Catergory> hierarchicalCategories, 
			Catergory parent, int sublevel)
	{
		Set<Catergory> children = parent.getChildcater();
		int newSublevel = sublevel + 1;
		
		for (Catergory subCategory : children)
		{
			String name = "";
			for(int i = 0; i < newSublevel; i++)
			{
				name += "--";
			}
			name += subCategory.getName();
			
			hierarchicalCategories.add(Catergory.copyFull(subCategory, name));
			
			listSubHierarchicalCategories(hierarchicalCategories, subCategory , newSublevel);
		}
	}
	
	public Catergory save(Catergory category) 
	{
		String idtest = Integer.toString(category.getID());
		boolean update = (idtest != null);
		if(update && category.getID() > 0)
		{
			return caterrepo.save(category);	
		}
		return caterrepo.save(category);
	}
	
	public List<Catergory> listCategoriesUsedInForm() 
	{
		List<Catergory> categoriesInUsedForm = new ArrayList<>();
		Iterable<Catergory> categoriesinDB = caterrepo.findAll(); 
		
		for (Catergory category : categoriesinDB)
		{
			if(category.getParent() == null)
			{
				System.out.println(category.getName());
				
				Set <Catergory> children = category.getChildcater();
				
				for (Catergory subCategory : children)
				{
					String name = "--" + subCategory.getName();
					categoriesInUsedForm.add(new Catergory(name));
					listSubCategoriesInForm(categoriesInUsedForm, subCategory, 1);
				}
			}
		}
		
		return categoriesInUsedForm;
	}

	private void listSubCategoriesInForm(List<Catergory> categoriesInUsedForm , Catergory parent, int sublevel) 
	{
		int newsublevel = sublevel + 1;
		Set <Catergory> children = parent.getChildcater();
		for (Catergory subCategory : children)
		{
			String name = "";
			for(int i = 0 ; i < newsublevel; i++)
			{
				name += "--";
			}
			categoriesInUsedForm.add(new Catergory(name));
			
			listSubCategoriesInForm(categoriesInUsedForm , subCategory, newsublevel);
		}
	}
	
	public Catergory get(int ID) throws CategoryNotFoundException
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
				
		Catergory categoryBYName = caterrepo.findByName(categoryname);
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
