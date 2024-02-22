package com.timeline.blog.blogApplicationAPI.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timeline.blog.blogApplicationAPI.payloads.APIResponse;
import com.timeline.blog.blogApplicationAPI.payloads.CategoryDTO;
import com.timeline.blog.blogApplicationAPI.services.CategoryServices;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryServices categoryservice;
	
	@PostMapping("/createCategory")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categorydto){
		CategoryDTO createdCategory = this.categoryservice.createCategory(categorydto);
		return new ResponseEntity<CategoryDTO>(createdCategory,HttpStatus.CREATED);
	}
	
	@PutMapping("/updateCategory/{categoryID}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categorydto, @PathVariable Integer categoryID){
		CategoryDTO updatedCategory = this.categoryservice.updateCategory(categorydto,categoryID);
		return new ResponseEntity<CategoryDTO>(updatedCategory,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{categoryID}")
	public ResponseEntity<APIResponse> deleteCategory(@PathVariable Integer categoryID){
		this.categoryservice.deleteCategory(categoryID);
		return new ResponseEntity<APIResponse>(new APIResponse("Category is deleted successfully", true),HttpStatus.OK);
	} 
	
	@GetMapping("/{categoryID}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer categoryID){
		CategoryDTO category = this.categoryservice.getCategoryById(categoryID);
		return new ResponseEntity<CategoryDTO>(category,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllCategories(){
		List<CategoryDTO> category = this.categoryservice.getAllCategory();
		return new ResponseEntity<>(category,HttpStatus.OK);
	}
		
}