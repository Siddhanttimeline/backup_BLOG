package com.timeline.blog.blogApplicationAPI.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timeline.blog.blogApplicationAPI.entity.Category;
import com.timeline.blog.blogApplicationAPI.exceptions.ResourceNotFoundException;
import com.timeline.blog.blogApplicationAPI.payloads.CategoryDTO;
import com.timeline.blog.blogApplicationAPI.repositories.CategoryRepo;
import com.timeline.blog.blogApplicationAPI.services.CategoryServices;


@Service
public class CategoryServiceImpl implements CategoryServices {

	@Autowired
	private CategoryRepo categoryrepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Override
	public CategoryDTO createCategory(CategoryDTO categorydto) {
		Category category = this.modelmapper.map(categorydto, Category.class);
		Category savedCategory = this.categoryrepo.save(category);
		return this.modelmapper.map(savedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categorydto, Integer categoryId) {
		Category category = this.categoryrepo.findById(categoryId)
			    .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		category.setCategoryTitle(categorydto.getCategoryTitle());
		category.setCategoryDescription(categorydto.getCategoryDescription());
		Category updatedCategory = this.categoryrepo.save(category);
		return this.modelmapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {		
		Category category = this.categoryrepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		this.categoryrepo.delete(category);
	}

	@Override
	public CategoryDTO getCategoryById(Integer categoryId) {
		Category category = this.categoryrepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category","Category Id", categoryId));

		return this.modelmapper.map(category, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		// TODO Auto-generated method stub
		
		List<Category> listOfCategories = this.categoryrepo.findAll();
		
		List<CategoryDTO> listOfCategoriesDTO = listOfCategories.stream().map((cat) -> this.modelmapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
		
		return listOfCategoriesDTO;
	}

}
