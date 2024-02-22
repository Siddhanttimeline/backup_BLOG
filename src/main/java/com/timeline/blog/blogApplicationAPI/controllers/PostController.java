package com.timeline.blog.blogApplicationAPI.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.timeline.blog.blogApplicationAPI.configurations.AppConstants;
import com.timeline.blog.blogApplicationAPI.payloads.APIResponse;
import com.timeline.blog.blogApplicationAPI.payloads.PostDTO;
import com.timeline.blog.blogApplicationAPI.payloads.PostResponse;
import com.timeline.blog.blogApplicationAPI.services.FileService;
import com.timeline.blog.blogApplicationAPI.services.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
    private static final Logger log = LogManager.getLogger(PostController.class);

	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId){
		PostDTO newPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(newPost,HttpStatus.CREATED);	
	}
	
	@GetMapping("/posts/user/{userId}")
	public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
			) {
		PostResponse postsDTO = this.postService.getPostByUser(userId,pageNumber,pageSize,sortBy,sortDir);
	    return new ResponseEntity<>(postsDTO, HttpStatus.OK);
	}

	@GetMapping("/posts/category/{categoryId}")
	public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
			) {
		PostResponse postsDTO = this.postService.getPostByCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
	    return new ResponseEntity<>(postsDTO, HttpStatus.OK);
	}

	// pageNumber starts from 0
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
			){
		log.info("Inside getAllPost controller");
		
		PostResponse posts = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(posts);
	}
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDTO> getPostByID(@PathVariable Integer postId){ 
		PostDTO post = this.postService.getPostById(postId);
		return new ResponseEntity<>(post,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<APIResponse> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<>(new APIResponse("Post deleted",true),HttpStatus.OK);
	}
	
	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postdto, @PathVariable Integer postId){ 
		PostDTO post = this.postService.updatePost(postdto, postId);
		return new ResponseEntity<PostDTO>(post,HttpStatus.OK);
	}
	
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPosts(@PathVariable String keyword) { 
        List<PostDTO> posts = postService.searchPosts(keyword);
        return ResponseEntity.ok(posts);
    }
    
    //Image Upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException{
		PostDTO post = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		post.setImageName(fileName);
		PostDTO updatedPost = this.postService.updatePost(post, postId);
		return new ResponseEntity<PostDTO>(updatedPost,HttpStatus.OK);
	}
    
    // method to serve files
	@GetMapping(value = "/post/image/{imageName}", produces= MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable String imageName, HttpServletResponse response
			) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

    
}
