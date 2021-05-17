package com.heptagon.userdetailstask.service;

import java.util.List;

import com.heptagon.userdetailstask.dto.UserDTO;
import com.heptagon.userdetailstask.exception.EmailNotUniqueException;

public interface UserService {
	    public UserDTO createUser(UserDTO userDto) throws EmailNotUniqueException;
		
	    public List<UserDTO> findAllUser();

}
