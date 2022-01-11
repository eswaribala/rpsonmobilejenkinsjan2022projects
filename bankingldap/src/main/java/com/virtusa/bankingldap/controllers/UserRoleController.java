package com.virtusa.bankingldap.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.bankingldap.models.UserRole;
import com.virtusa.bankingldap.services.UserRoleService;

@RestController
public class UserRoleController {
    @Autowired
	private UserRoleService userRoleService;
    @CrossOrigin("*")
    @GetMapping("/getuserroles/{userName}")
    public String[] getUserRolesByName(@PathVariable("userName") String userName)
    {
    	return userRoleService.findUserRolesByName(userName).stream()
    			.map(a -> a.getRole()).toArray(String[]::new);
    }
}
