package com.timeline.blog.blogApplicationAPI.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.timeline.blog.blogApplicationAPI.entity.Category;
import com.timeline.blog.blogApplicationAPI.entity.Post;
import com.timeline.blog.blogApplicationAPI.entity.User;
import com.timeline.blog.blogApplicationAPI.exceptions.ResourceNotFoundException;
import com.timeline.blog.blogApplicationAPI.payloads.PostDTO;
import com.timeline.blog.blogApplicationAPI.payloads.PostResponse;
import com.timeline.blog.blogApplicationAPI.repositories.CategoryRepo;
import com.timeline.blog.blogApplicationAPI.repositories.PostRepo;
import com.timeline.blog.blogApplicationAPI.repositories.UserRepository;
import com.timeline.blog.blogApplicationAPI.services.PostService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

	
	@Override
	public PostDTO createPost(PostDTO postdto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow( () -> new ResourceNotFoundException("User","User Id",userId)) ;

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow( () -> new ResourceNotFoundException("Category","Category Id",categoryId)) ;

		Post post =this.modelMapper.map(postdto, Post.class);
		post.setImageName("default.png");
		post.setDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postdto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","Post Id",postId));		
		
		post.setPostTitle(postdto.getPostTitle());
		post.setPostContent(postdto.getPostContent());
		post.setImageName(postdto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","Post Id",postId));
			
		this.postRepo.delete(post);	
	}

	
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		log.info("Inside getAllPost - PostServiceImpl");
		log.info(sortDir,sortBy);

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
	    Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		log.info("pageable"+pageable);
	    Page<Post> postPage = postRepo.findAll(pageable);
		log.info("postPage"+postPage);
	    List<Post> posts = postPage.getContent();
		log.info("Posts : "+posts);

	    List<PostDTO> postDTOs = posts.stream()
	            .map(post -> modelMapper.map(post, PostDTO.class))
	            .collect(Collectors.toList());
	    log.info("postsDTO : " + postDTOs);

	    PostResponse postResponse = new PostResponse();
	    postResponse.setContent(postDTOs);
	    postResponse.setPageNumber(postPage.getNumber());
	    postResponse.setPageSize(postPage.getSize());
	    postResponse.setTotalElements(postPage.getTotalElements());
	    postResponse.setTotalPages(postPage.getTotalPages());
	    postResponse.setLastPage(postPage.isLast());
		log.info("postResponse : "+postResponse);

	    return postResponse;
	}

	@Override
	public PostDTO getPostById(Integer postId){
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","Post Id",postId));
		return this.modelMapper.map(post, PostDTO.class);
	}

	@Override
	public PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize,String sortBy, String sortDir) {
		Category category = this.categoryRepo.findById(categoryId).
				orElseThrow(() -> new ResourceNotFoundException("Category","Category Id",categoryId));

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		
	    Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);

	    Page<Post> postPage = this.postRepo.findByCategory(category,pageable);
		List<Post> posts = postPage.getContent();
		List<PostDTO> postsDTO = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		
		 	PostResponse postResponse = new PostResponse();
		    postResponse.setContent(postsDTO);
		    postResponse.setPageNumber(postPage.getNumber());
		    postResponse.setPageSize(postPage.getSize());
		    postResponse.setTotalElements(postPage.getTotalElements());
		    postResponse.setTotalPages(postPage.getTotalPages());
		    postResponse.setLastPage(postPage.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		User user = this.userRepo.findById(userId).
				orElseThrow(() -> new ResourceNotFoundException("User","User Id",userId));
		
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
	    Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> postPage =  postRepo.findByUser(user,pageable);
	    List<Post> posts = postPage.getContent();

		List<PostDTO> postsDTO = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		
		  	PostResponse postResponse = new PostResponse();
		    postResponse.setContent(postsDTO);
		    postResponse.setPageNumber(postPage.getNumber());
		    postResponse.setPageSize(postPage.getSize());
		    postResponse.setTotalElements(postPage.getTotalElements());
		    postResponse.setTotalPages(postPage.getTotalPages());
		    postResponse.setLastPage(postPage.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDTO> searchPosts(String keyword) {		
		List<Post> post = this.postRepo.findByPostTitleContaining(keyword);
		List<PostDTO> postDTOs = post.stream().map((p)-> this.modelMapper.map(p,PostDTO.class)).collect(Collectors.toList());
 		return postDTOs;
	}

}
