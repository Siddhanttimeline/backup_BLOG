package com.timeline.blog.blogApplicationAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timeline.blog.blogApplicationAPI.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
