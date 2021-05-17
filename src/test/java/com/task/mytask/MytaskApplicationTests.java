package com.task.mytask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.heptagon.userdetailstask.dto.EmployeeDTO;
import com.heptagon.userdetailstask.dto.UserDTO;
import com.heptagon.userdetailstask.entity.Employee;
import com.heptagon.userdetailstask.entity.User;
import com.heptagon.userdetailstask.repository.EmployeeRepository;
import com.heptagon.userdetailstask.repository.UserRepository;
import com.heptagon.userdetailstask.security.services.EmployeeServiceImpl;
import com.heptagon.userdetailstask.serviceImpl.UserServiceImpl;
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = User.class)
class MytaskApplicationTests {
	
	   @InjectMocks
	   private ModelMapper modelMapper;
	     
	    @Mock
	    UserRepository userRepository;
	 
	    @Before
	    public void init() {
	        MockitoAnnotations.initMocks(this);
	    }
	    @Test
	    public void getAllUserTest() throws NullPointerException
	    {
	        List<UserDTO> list = new ArrayList<UserDTO>();
	        UserDTO empOne = new UserDTO(1L,"sanjay","sv@gmail.com","ROLE_EMPLOYEE_ADMIN","Bangalore","8967457866");
	        UserDTO empTwo = new UserDTO(2L,"sanjay1","sv1@gmail.com","ROLE_EMPLOYEE_ADMIN","Bangalore","8967457867");
	        UserDTO empThree = new UserDTO(3L,"sanjay2","sv2@gmail.com","ROLE_EMPLOYEE_ADMIN","Bangalore","8967457868");
	         
	        list.add(empOne);
	        list.add(empTwo);
	        list.add(empThree);
	        List<User> users = list.stream().map(userDto -> modelMapper.map(userDto, User.class))
					.collect(Collectors.toList());
	        when(userRepository.findAll()).thenReturn(users);
	         
	        //test
	        
	        List<User> empList = userRepository.findAll();
	         
	        Assertions.assertEquals(3, empList.size());
	        verify(userRepository, times(1)).findAll();
	    }
}
