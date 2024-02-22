package com.timeline.blog.blogApplicationAPI.services;

import java.util.List;

import com.timeline.blog.blogApplicationAPI.payloads.CategoryDTO;

public interface CategoryServices {
	
	CategoryDTO createCategory(CategoryDTO categorydto);
	CategoryDTO updateCategory(CategoryDTO categorydto, Integer categoryId);
	void deleteCategory(Integer categoryId);
	CategoryDTO getCategoryById(Integer categoryId);
	List<CategoryDTO> getAllCategory();

}
