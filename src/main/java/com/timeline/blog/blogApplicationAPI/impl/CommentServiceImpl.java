package com.timeline.blog.blogApplicationAPI.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timeline.blog.blogApplicationAPI.entity.Comment;
import com.timeline.blog.blogApplicationAPI.entity.Post;
import com.timeline.blog.blogApplicationAPI.entity.User;
import com.timeline.blog.blogApplicationAPI.exceptions.ResourceNotFoundException;
import com.timeline.blog.blogApplicationAPI.payloads.CommentDTO;
import com.timeline.blog.blogApplicationAPI.repositories.CommentRepo;
import com.timeline.blog.blogApplicationAPI.repositories.PostRepo;
import com.timeline.blog.blogApplicationAPI.repositories.UserRepository;
import com.timeline.blog.blogApplicationAPI.services.CommentService;


@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDTO createComment(CommentDTO commentDTO,Integer postId, Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow( () -> new ResourceNotFoundException("User","User Id",userId)) ;
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","Post Id",postId));
		
		Comment comment = this.modelMapper.map(commentDTO, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment = this.commentRepo.save(comment);		
		return this.modelMapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment","Comment Id",commentId));
		
		this.commentRepo.delete(comment);

	}

}
