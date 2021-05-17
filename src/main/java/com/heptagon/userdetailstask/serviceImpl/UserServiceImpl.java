package com.heptagon.userdetailstask.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.heptagon.userdetailstask.dto.UserDTO;
import com.heptagon.userdetailstask.entity.Role;
import com.heptagon.userdetailstask.entity.User;
import com.heptagon.userdetailstask.exception.EmailNotUniqueException;
import com.heptagon.userdetailstask.exception.MyException;
import com.heptagon.userdetailstask.repository.RoleRepository;
import com.heptagon.userdetailstask.repository.UserRepository;
import com.heptagon.userdetailstask.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MessageSource messageSource;
	private Locale locale;

	@Override
	public UserDTO createUser(UserDTO userDto) throws EmailNotUniqueException {
		User user = null;
		UserDTO result_userDto=null;

		if (userRepository.existsByUsername(userDto.getUsername())) {
			throw new EmailNotUniqueException(userDto.getUsername());
		}
		user = modelMapper.map(userDto, User.class);

		try {
			String role = "ROLE_".concat(userDto.getRole());
			Role strRole = new Role();

			if (role.equals("ROLE_EMPLOYEE_ADMIN")) {
				
				Role eadminRole = roleRepository.findByName(role).get();
				strRole.setId(eadminRole.getId());
				strRole.setName(eadminRole.getName());
				
			} else if (role.equals("ROLE_EMPLOYEE_USER")) {
				
				Role eUserRole = roleRepository.findByName(role).get();
				strRole.setId(eUserRole.getId());
				strRole.setName(eUserRole.getName());
				
			}else {
				throw new MyException(messageSource.getMessage("createUser.notAllowed", new String[0], locale));
			}
			user.setPassword(encoder.encode(userDto.getPassword()));
			user.setRole(strRole);
			user = userRepository.save(user);
			result_userDto=modelMapper.map(user, UserDTO.class);
		} catch (DataAccessException e) {
			throw new MyException(e.getCause().getCause().getMessage());
		} catch (Exception e) {
			throw new MyException(e.getMessage());
		}
		return result_userDto;

	}

	@Override
	public List<UserDTO> findAllUser() {
		List<UserDTO> userdto =new ArrayList<>();
		try {
			List<User> users=userRepository.findAll();
			if(users!=null) {
			userdto = users.stream().map(user -> modelMapper.map(user, UserDTO.class))
						.collect(Collectors.toList());
			}
		}catch (Exception e) {
			throw new MyException(e.getMessage());
		}
		
		return userdto;
	}
}
