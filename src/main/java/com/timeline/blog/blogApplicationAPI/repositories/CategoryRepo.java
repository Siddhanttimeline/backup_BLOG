package com.timeline.blog.blogApplicationAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timeline.blog.blogApplicationAPI.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
