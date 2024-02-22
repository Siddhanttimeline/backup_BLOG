package com.timeline.blog.blogApplicationAPI.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.timeline.blog.blogApplicationAPI.entity.Comment;

public class PostDTO {
	
	private Integer postId;
	private String postTitle;
	private String postContent;
	private String imageName;
	private Date date;
	private CategoryDTO category;
	private UserDTO user;
	private Set<CommentDTO> comments = new HashSet<>();
	
	
	public Set<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public CategoryDTO getCategory() {
		return category;
	}
	public void setCategory(CategoryDTO category) {
		this.category = category;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public PostDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
