package com.virtusa.bankingldap.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.virtusa.bankingldap.models.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole,String> {

	@Query("SELECT ur FROM UserRole ur WHERE ur.user.userName = :name")
	  List<UserRole> getUserRolesByName(@Param("name") String name);
}
