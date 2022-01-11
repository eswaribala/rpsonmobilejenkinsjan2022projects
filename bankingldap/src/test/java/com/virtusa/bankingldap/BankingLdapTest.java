package com.virtusa.bankingldap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.virtusa.bankingldap.models.User;
import com.virtusa.bankingldap.repositories.UserRepository;
import com.virtusa.bankingldap.services.UserService;

@SpringBootTest
class BankingLdapTest {

	 @Mock
	    private UserRepository userRepository;

	    @InjectMocks // auto inject userRepository
	    private UserService userService = new UserService();

	    @BeforeEach
	    void setMockOutput() {
	    	User user =new User();
	    	user.setUserName("Anju134");
	    	user.setPassword("test@123");
	    	user.setEnabled(true);
	    	Optional<User> userOptional = Optional.of(user);
	    	when(userRepository.findById("Anju134")).thenReturn(userOptional);
	       
	    }

	    @DisplayName("Test Mock userService + userRepository")
	    @Test
	    void testGet() {
	        assertEquals("test@123", userService.getUserByName("Anju134").getPassword());
	    }


}
