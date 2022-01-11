package com.virtusa.bankingldap.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtusa.bankingldap.models.UserRole;
import com.virtusa.bankingldap.repositories.UserRoleRepository;

@Service
public class UserRoleService {
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	public List<UserRole> findUserRolesByName(String userName)
	{
		return userRoleRepository.getUserRolesByName(userName);
	}

}
