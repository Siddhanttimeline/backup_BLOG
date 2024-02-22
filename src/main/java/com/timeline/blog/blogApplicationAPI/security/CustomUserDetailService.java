package com.timeline.blog.blogApplicationAPI.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.timeline.blog.blogApplicationAPI.entity.User;
import com.timeline.blog.blogApplicationAPI.exceptions.ResourceNotFoundException;
import com.timeline.blog.blogApplicationAPI.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// loading user from database by username
	
		User user = this.userRepo.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User","email : "+username,0));
		

		
		
		return user;
	}

}
