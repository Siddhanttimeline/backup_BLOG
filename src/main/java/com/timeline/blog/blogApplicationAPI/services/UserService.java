package com.timeline.blog.blogApplicationAPI.services;

import java.util.List;
import com.timeline.blog.blogApplicationAPI.payloads.UserDTO;

public interface UserService {
	
	UserDTO registerNewUser(UserDTO user);

	UserDTO createUser(UserDTO user);

	UserDTO updateUser(UserDTO user, Integer userId);

	UserDTO getUserById(Integer userID);

	List<UserDTO> getAllUsers();

	void deleteUser(Integer userId);

}
