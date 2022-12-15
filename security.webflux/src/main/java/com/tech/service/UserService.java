package com.tech.service;

import java.util.Map;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tech.entity.User;

import reactor.core.publisher.Mono;

@Service
public class UserService implements ReactiveUserDetailsService {
	
	private Map<String, com.tech.entity.User> data;
	
	
	
	User user = new User();
//	public void init() {
//		
//		data = new HashMap<>();
//		data.put("user", new User());
//	}
//	
	@Override
	public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException {
		
		return Mono.justOrEmpty(user);
	}

}
