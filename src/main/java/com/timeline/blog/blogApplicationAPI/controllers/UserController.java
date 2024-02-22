package com.timeline.blog.blogApplicationAPI.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timeline.blog.blogApplicationAPI.payloads.APIResponse;
import com.timeline.blog.blogApplicationAPI.payloads.UserDTO;
import com.timeline.blog.blogApplicationAPI.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addUser")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userdto){
		UserDTO createdUserDTO = this.userService.createUser(userdto);
		return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateUser/{userID}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userdto, @PathVariable Integer userID  ){
		UserDTO updatedUser = this.userService.updateUser(userdto, userID);
		return new ResponseEntity<>(updatedUser,HttpStatus.OK);
	}
	
	@GetMapping("/userList")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
	    List<UserDTO> list = this.userService.getAllUsers();
	    return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userID}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Integer userID){
		UserDTO userdto = this.userService.getUserById(userID);
		return new ResponseEntity<>(userdto,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/userDelete/{userID}")
	public ResponseEntity<APIResponse> deleteUser(@PathVariable Integer userID){
		this.userService.deleteUser(userID);
		return new ResponseEntity<>(new APIResponse("User deleted",true),HttpStatus.OK);
	}

}
