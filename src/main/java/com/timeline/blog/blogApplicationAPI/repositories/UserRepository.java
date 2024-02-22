package com.timeline.blog.blogApplicationAPI.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timeline.blog.blogApplicationAPI.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);
	
}
