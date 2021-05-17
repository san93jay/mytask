/**
 * 
 */
package com.heptagon.userdetailstask.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.heptagon.userdetailstask.dto.JwtResponseDTO;
import com.heptagon.userdetailstask.dto.LoginDTO;
import com.heptagon.userdetailstask.dto.UserDTO;
import com.heptagon.userdetailstask.entity.UserToken;
import com.heptagon.userdetailstask.repository.EmployeeRepository;
import com.heptagon.userdetailstask.repository.RoleRepository;
import com.heptagon.userdetailstask.security.jwt.JwtProvider;
import com.heptagon.userdetailstask.service.UserService;
import com.heptagon.userdetailstask.service.UserTokenService;

import lombok.extern.log4j.Log4j2;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */
@RestController
@Log4j2
@RequestMapping(value = "/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserTokenService userTokenService;
	
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	private MessageSource messageSource;
	private Locale locale;
	
	@PostMapping("/signUp")
	public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserDTO userDTO){
		UserDTO userDto=null;
		try {
			if(userDTO!=null) {
				userDto=userService.createUser(userDTO);
			}		
			
		}catch (Exception e) {
			log.error(messageSource.getMessage("employee.saveData", new String[0], locale));
		}
		return new ResponseEntity<>(userDto, HttpStatus.OK);
		
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginRequest, HttpServletRequest request) {
		JwtResponseDTO jwtResponse = null;
		try {

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwt = jwtProvider.generateJwtToken(authentication);

			UserToken userToken = userTokenService.saveUserToken(loginRequest, request, jwt);
			jwtResponse = new JwtResponseDTO(jwt);
			jwtResponse.setUserId(userToken.getUser().getId());
			jwtResponse.setName(userToken.getUser().getName());
			jwtResponse.setUserName(userToken.getUser().getUsername());
			jwtResponse.setUserRole(userToken.getUser().getRole().getName().name());
		} catch (Exception e) {
			log.info(messageSource.getMessage("employee.signIn", new String[0], locale));
		}
		return ResponseEntity.ok(jwtResponse);
	}
}
