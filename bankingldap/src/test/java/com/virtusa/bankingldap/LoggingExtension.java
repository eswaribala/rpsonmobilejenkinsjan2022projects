package com.virtusa.bankingldap;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
//centralized logging
public class LoggingExtension implements BeforeAllCallback, BeforeEachCallback{

	public LoggingExtension(String name)
	{
		
	}
	
	@Override
	public void beforeEach(ExtensionContext context) {
		// TODO Auto-generated method stub
		System.out.println(context.getDisplayName());
	}

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(context.getDisplayName());
	}

}
