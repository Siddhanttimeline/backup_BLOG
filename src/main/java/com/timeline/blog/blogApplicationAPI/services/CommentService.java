package com.timeline.blog.blogApplicationAPI.services;

import com.timeline.blog.blogApplicationAPI.payloads.CommentDTO;

public interface CommentService {

	CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId);
	void deleteComment(Integer commentId);
	
}
