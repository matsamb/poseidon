package com.auth2.oseidclient.service;

import org.springframework.stereotype.Component;

@Component
public class OseidUserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	OseidUserNotFoundException(){}
	
	public OseidUserNotFoundException(String message) {
		 super(message);
	}
}