package com.auth2.oseidclient.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockOseidclientUserSecurityContextFactory.class)
public @interface WithMockOseidclientUser {

	String username() default "max";

	String name() default "max";
	
	String email() default "max";
	
	String password() default "password";
	
	String userRole() default "ROLE_ADMIN";
	
	boolean enabled() default true;
	
	boolean locked() default false;
	
}
