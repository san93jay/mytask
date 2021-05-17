package com.heptagon.userdetailstask.security.services;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.heptagon.userdetailstask.entity.User;
import com.heptagon.userdetailstask.repository.UserRepository;


@Service
public class EmployeeServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    HttpSession session;
    

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
    	
    	User user = userRepository.findByUsername(username)
                	.orElseThrow(() -> 
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
        );
        
        return UserPrinciple.build(user);
    }
}
