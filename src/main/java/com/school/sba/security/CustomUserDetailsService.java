package com.school.sba.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.school.sba.Repository.UserRepository;
import com.school.sba.entity.User;
import com.school.sba.exception.UserNotFoundById;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepository.findByUserName(username).map( user-> new CustomUserDetails(user))
		
		.orElseThrow( ()-> new UserNotFoundById("User Not Found")   );
		
	}
	
	

}
