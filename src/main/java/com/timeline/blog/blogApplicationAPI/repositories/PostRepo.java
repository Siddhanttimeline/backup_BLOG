package com.timeline.blog.blogApplicationAPI.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.timeline.blog.blogApplicationAPI.entity.Category;
import com.timeline.blog.blogApplicationAPI.entity.Post;
import com.timeline.blog.blogApplicationAPI.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	 
	Page<Post> findByUser(User user, Pageable pageable);
	Page<Post> findByCategory(Category category, Pageable pageable);
	List<Post> findByPostTitleContaining(String keyword); // PostTitle - Post(Table), Title(field)
	
}
