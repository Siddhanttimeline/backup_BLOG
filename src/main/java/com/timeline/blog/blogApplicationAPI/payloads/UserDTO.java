package com.timeline.blog.blogApplicationAPI.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.timeline.blog.blogApplicationAPI.entity.Role;

public class UserDTO {

	private int id;
	
	@NotEmpty
	@Size(min = 4, message = "User name must be chararcter of 4.")
	private String name;
	
	@Email(message="Email address is not valid.")
	private String email;
	
	@NotEmpty
	@Size(min=3,max=10,message="Password must be between 3 to 10 characters")
//    @JsonIgnore
	private String password;
	
	@NotEmpty
	private String about;
	
	
	private Set<RoleDTO> roles = new HashSet<>();
	
	public Set<RoleDTO> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDTO> roles) {
		this.roles = roles;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public UserDTO() {
		super();
	}
	
	
}
