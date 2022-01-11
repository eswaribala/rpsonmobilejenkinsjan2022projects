package com.virtusa.bankingldap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.bankingldap.models.User;
import com.virtusa.bankingldap.services.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@CrossOrigin("*")
	@GetMapping("/getuser/{userName}")
	public User getUserByUserName(@PathVariable("userName") String userName)
	{
		return userService.getUserByName(userName);
	}
	
	

}
