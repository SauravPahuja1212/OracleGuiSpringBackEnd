package com.oraclegui.service;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oraclegui.entity.UserDao;
import com.oraclegui.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		if("User".equals(username)) {
//			return new User("User", "$2y$12$qStjpnLluJ906w/2BQnhQ.vs0Jkftkij4px5vanpUU03LS.lQa4Zm",
//					new ArrayList<>());
//		}
//		else {
//			throw new UsernameNotFoundException("User Not Found");
//		}
//	}
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDao user = userRepository.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User not found with username: "+username);
		}
		
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}
	
	@Transactional
	public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
		UserDao user = userRepository.findById(id);
		
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}
}
