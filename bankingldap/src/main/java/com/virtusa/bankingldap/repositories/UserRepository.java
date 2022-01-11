package com.virtusa.bankingldap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.virtusa.bankingldap.models.User;

public interface UserRepository extends JpaRepository<User,String>{

}
