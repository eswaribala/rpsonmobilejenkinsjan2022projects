package com.iocl.bcryptdemo;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptDemo {

	public static void main(String[] args) {
		  String  originalPassword = "password";
	        String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
	        System.out.println(generatedSecuredPasswordHash);
	         
	        boolean matched = BCrypt.checkpw(originalPassword, generatedSecuredPasswordHash);
	        System.out.println(matched);
	        int i = 0;
	    	while (i < 10) {
	    		String password = "123456";
	    		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    		String hashedPassword = passwordEncoder.encode(password);

	    		System.out.println(hashedPassword);
	    		i++;
	    	}

	}

}
