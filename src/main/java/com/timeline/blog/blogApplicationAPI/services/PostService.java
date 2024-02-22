package com.timeline.blog.blogApplicationAPI.services;

import java.util.List;

import com.timeline.blog.blogApplicationAPI.payloads.PostDTO;
import com.timeline.blog.blogApplicationAPI.payloads.PostResponse;

public interface PostService {
	
	PostDTO createPost(PostDTO postdto, Integer userId, Integer categoryId); // done
	PostDTO updatePost(PostDTO postdto, Integer postId);
	void deletePost(Integer postId);
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir); // done
	PostDTO getPostById(Integer postId); 
	PostResponse getPostByCategory(Integer categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortDir); // done
	PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir); // done
	List<PostDTO> searchPosts(String keyword);
	
}
