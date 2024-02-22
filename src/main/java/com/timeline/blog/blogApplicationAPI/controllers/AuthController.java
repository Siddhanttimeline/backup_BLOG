package com.timeline.blog.blogApplicationAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timeline.blog.blogApplicationAPI.exceptions.APIException;
import com.timeline.blog.blogApplicationAPI.payloads.JWTAuthRequest;
import com.timeline.blog.blogApplicationAPI.payloads.JWTAuthResponse;
import com.timeline.blog.blogApplicationAPI.payloads.UserDTO;
import com.timeline.blog.blogApplicationAPI.security.JWTTokenHelper;
import com.timeline.blog.blogApplicationAPI.services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest request) throws Exception {
		
	    this.authenticate(request.getUserName(), request.getPassword());
	    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
	    String token = this.jwtTokenHelper.generateToken(userDetails);
	    
	    JWTAuthResponse response = new JWTAuthResponse();
	    response.setToken(token);
	    
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}


	private void authenticate(String userName, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		}catch(BadCredentialsException e) {
			System.out.println("Invalid details");
			e.printStackTrace();
			throw new APIException("Invalid Username and password");
		}
	}
	
	// Register user API
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerNewUser(@RequestBody UserDTO userDTO) {
	    UserDTO registeredUser = this.userService.registerNewUser(userDTO);
	    return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
	}

	
}
