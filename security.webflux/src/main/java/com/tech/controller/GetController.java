package com.tech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/test")
public class GetController {
	
	
	@GetMapping(value = "/")
	 public String getStirng() {
		 return "get api fetched successfully";
	 }

}
