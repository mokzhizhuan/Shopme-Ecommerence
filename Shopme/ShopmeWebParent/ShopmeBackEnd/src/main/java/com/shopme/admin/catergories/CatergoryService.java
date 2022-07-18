package com.shopme.admin.catergories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Catergory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CatergoryService 
{
	@Autowired
	private CatergoryRepository caterrepo;
	
	public List<Catergory> listAll()
	{
		return(List<Catergory>) caterrepo.findAll();
	}

	public Catergory save(Catergory category) 
	{
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
					listChildren(categoriesInUsedForm, subCategory, 1);
				}
			}
		}
		
		return categoriesInUsedForm;
	}

	private void listChildren(List<Catergory> categoriesInUsedForm , Catergory parent, int sublevel) 
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
			
			listChildren(categoriesInUsedForm , subCategory, newsublevel);
		}
	}
}
