package com.iocl.aesdemo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iocl.aesdemo.models.Credentials;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Controller
public class WelcomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class);

	@RequestMapping(value={"/login"},method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request){
		System.out.println("Received request for login page with id - " + request.getSession().getId());
		String randomKey = UUID.randomUUID().toString();
		//String uniqueKey = randomKey.substring(randomKey.length()-17, randomKey.length() -1);
        String uniqueKey = "1234567891234567";
	    request.getSession().setAttribute("key", uniqueKey);
        /*LoginToken token = new LoginToken();
        token.setActive(true);
        token.setCreatedOn(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        token.setExpiredOn(Date.from(LocalDateTime.now().plus(30, ChronoUnit.MINUTES).atZone(ZoneId.systemDefault()).toInstant()));
        token.setTokenValue(uniqueKey);
        token.setSessionId(request.getSession().getId());
        tokenRepo.save(token);*/
	    return "index";
    }

    @RequestMapping(value={"/login"},method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?>  login(@RequestBody Credentials credentials) {
    	System.out.println(credentials.getPassword());

        String decryptedPassword =  new String(java.util.Base64.getDecoder().decode(credentials.getPassword()));

		//String decryptedPassword =  new String(java.util.Base64.getDecoder().decode(credentials.getPassword()));
		AesUtil aesUtil = new AesUtil(128, 1000);
		System.out.println(decryptedPassword);
		if (decryptedPassword != null && decryptedPassword.split("::").length == 3) {
		    LOGGER.info("Password decrypted successfully for username - " + credentials.getUserName());
		    String password = aesUtil.decrypt(decryptedPassword.split("::")[1], decryptedPassword.split("::")[0], "1234567891234567", decryptedPassword.split("::")[2]);
		    System.out.println(password);
		}
    	
    	

        return ResponseEntity.ok("Success");

    }

}
