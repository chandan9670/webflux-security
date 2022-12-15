package com.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tech.entity.AuthRequest;
import com.tech.entity.AuthResponse;
import com.tech.entity.User;
import com.tech.service.UserService;
import com.tech.util.JwtUtil;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
		
	 @PostMapping("/login")
	    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar) {
	        return userService.findByUsername(ar.getUsername())
	            .filter(userDetails ->ar.getPassword().equals(userDetails.getPassword()))
	            .map(userDetails -> ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken((User) userDetails))))
	            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
	    }

	 
}

