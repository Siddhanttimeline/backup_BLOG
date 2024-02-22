package com.timeline.blog.blogApplicationAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timeline.blog.blogApplicationAPI.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
