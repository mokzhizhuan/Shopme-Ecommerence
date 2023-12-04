package com.shopme.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopme.category.CategoryNotFoundException;
import com.shopme.category.CategoryService;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Products;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProductController {
	
	@Autowired 
	private ProductService productService;
	
	@Autowired 
	private CategoryService categoryService;
	//@Autowired private ReviewService reviewService;	
	//@Autowired private ReviewVoteService voteService;
	//@Autowired private ControllerHelper controllerHelper;

	@GetMapping("/c/{category_alias}")
	public String viewCategoryFirstPage(@PathVariable("category_alias") String alias,
			Model model) {
		return viewCategoryByPage(alias, 1, model);
	}
	
	@GetMapping("/c/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias,
			@PathVariable("pageNum") int pageNum, Model model) {
		try {
			Category category = categoryService.getCategory(alias);		
			List<Category> listCategoryParents = categoryService.getCategoryParents(category);
			
			Page<Products> pageProducts = productService.listByCategory(pageNum, category.getId());
			List<Products> listProducts = pageProducts.getContent();
			
			long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
			long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
			if (endCount > pageProducts.getTotalElements()) {
				endCount = pageProducts.getTotalElements();
			}
			
			
			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", pageProducts.getTotalElements());
			model.addAttribute("pageTitle", category.getName());
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("category", category);
			
			return "products";
		} catch (CategoryNotFoundException ex) {
			return "error/404";
		}
	}
	
	@GetMapping("/p/{product_ailas}")
	public String viewProductDetail(@PathVariable("product_alias") String ailas, Model model,
			HttpServletRequest request) {
		
		try {
			Products product = productService.getProduct(ailas);
			List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());
			//Page<Review> listReviews = reviewService.list3MostVotedReviewsByProduct(product);
			
			//Customer customer = controllerHelper.getAuthenticatedCustomer(request);
			
			/*if (customer != null) {
				boolean customerReviewed = reviewService.didCustomerReviewProduct(customer, product.getId());
				//voteService.markReviewsVotedForProductByCustomer(listReviews.getContent(), product.getId(), customer.getId());
				
				if (customerReviewed) {
					model.addAttribute("customerReviewed", customerReviewed);
				} else {
					boolean customerCanReview = reviewService.canCustomerReviewProduct(customer, product.getId());
					model.addAttribute("customerCanReview", customerCanReview);
				}
			}*/
			
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("product", product);
			//model.addAttribute("listReviews", listReviews);
			model.addAttribute("pageTitle", product.getName());
			
			return "product_detail";
		} catch (ProductNotFoundException e) {
			return "error/404";
		}
	}
	
}
