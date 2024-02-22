package com.timeline.blog.blogApplicationAPI.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.timeline.blog.blogApplicationAPI.configurations.AppConstants;
import com.timeline.blog.blogApplicationAPI.entity.Role;
import com.timeline.blog.blogApplicationAPI.entity.User;
import com.timeline.blog.blogApplicationAPI.payloads.UserDTO;
import com.timeline.blog.blogApplicationAPI.repositories.RoleRepo;
import com.timeline.blog.blogApplicationAPI.repositories.UserRepository;
import com.timeline.blog.blogApplicationAPI.services.UserService;
import com.timeline.blog.blogApplicationAPI.exceptions.ResourceNotFoundException;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
 
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		// TODO Auto-generated method stub

		User user = this.dtoToEntity(userDTO);
		User savedUser = this.userRepo.save(user);

		return this.EntityTodto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		User updatedUser = this.userRepo.save(user);
		return EntityTodto(updatedUser);
	}

	@Override
	public UserDTO getUserById(Integer userID) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userID));
		return EntityTodto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> userList = this.userRepo.findAll();

		List<UserDTO> userDTOList = new ArrayList<>();
		for (User user : userList) {
			UserDTO userdto = EntityTodto(user);
			userDTOList.add(userdto);
		}
		return userDTOList;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		this.userRepo.delete(user);
	}

	private User dtoToEntity(UserDTO userDTO) {
		
		User user = this.modelMapper.map(userDTO, User.class);
		
//		User user = new User();
//		user.setId(userDTO.getId());
//		user.setName(userDTO.getName());
//		user.setEmail(userDTO.getEmail());
//		user.setAbout(userDTO.getAbout());
//		user.setPassword(userDTO.getPassword());
		return user;
	}

	private UserDTO EntityTodto(User user) {
		UserDTO userdto = this.modelMapper.map(user, UserDTO.class);
		return userdto;
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDTO) {
		
		User user = this.modelMapper.map(userDTO, User.class);
		
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Role role = this.roleRepo.findById(AppConstants.REGULAR_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		
		return this.modelMapper.map(newUser, UserDTO.class);
	}

}
