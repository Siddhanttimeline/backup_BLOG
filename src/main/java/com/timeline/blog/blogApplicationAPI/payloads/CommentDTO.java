package com.timeline.blog.blogApplicationAPI.payloads;

public class CommentDTO {

	private Integer id;

	private String content;
	
	private UserDTO user;
	
//	private Date commentDate;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

//	public Date getCommentDate() {
//		return commentDate;
//	}
//
//	public void setCommentDate(Date commentDate) {
//		this.commentDate = commentDate;
//	}

	public CommentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
