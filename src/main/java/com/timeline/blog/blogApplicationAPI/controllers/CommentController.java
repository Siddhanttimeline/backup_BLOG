package com.timeline.blog.blogApplicationAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timeline.blog.blogApplicationAPI.payloads.APIResponse;
import com.timeline.blog.blogApplicationAPI.payloads.CommentDTO;
import com.timeline.blog.blogApplicationAPI.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/comments/post/{postId}/user/{userId}")
	public ResponseEntity<CommentDTO> createComment(
			@RequestBody CommentDTO commentdto,
			@PathVariable Integer postId,
			@PathVariable Integer userId
			){
		CommentDTO savedComment = this.commentService.createComment(commentdto, postId, userId);
	    return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
	}
	
	
//	@PostMapping("/comments/post/{postId}")
//	public ResponseEntity<CommentDTO> createComment(
//			@RequestBody CommentDTO commentdto,
//			@PathVariable Integer postId
//			){
//		CommentDTO savedComment = this.commentService.createComment(commentdto, postId);
//	    return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
//	}
	
	@DeleteMapping("/comments/delete/{commentId}")
	public ResponseEntity<APIResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
	    return new ResponseEntity<APIResponse>(new APIResponse("Comment deleted successfully",true), HttpStatus.OK);		
	}
	
}
