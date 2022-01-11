package com.virtusa.bankingldap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtusa.bankingldap.models.User;
import com.virtusa.bankingldap.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public User getUserByName(String userName)
	{
		return userRepository.findById(userName).orElse(null);
	}
	
	//update 
	
	public User updateUser(User user)
	{
		return userRepository.save(user);
	}
	
	//delete deleteUser
	
	public boolean deleteUser(String userName)
	{
		boolean status=false;
		userRepository.deleteById(userName);
		
		if(getUserByName(userName)==null)
			status=true;
		return status;
	}
	
	

}
