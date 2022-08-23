package com.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.review.model.Response;
import com.review.model.User;
import com.review.repository.UserRepository;

@Controller
@RequestMapping(value="/review/admin")
public class AdminController {
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping(value="/addUser", consumes = "application/json", produces = "application/json")
	public @ResponseBody Response addUser(@RequestBody User user) {
		Response response = new Response();
		User savedUser = userRepository.save(user);
		if(user.getEmail().equals(savedUser.getEmail())) {
			response.setMessage("User saved successfully.");
			response.setStatus(true);
		} else {
			response.setMessage("User not saved. Please check the data and try again.");
			response.setStatus(false);
		}
		return response;
	}
}
