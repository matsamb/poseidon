package com.auth2.oseidclient.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.auth2.oseidclient.entity.OseidUserDetails;

public class WithMockOseidclientUserSecurityContextFactory implements
	WithSecurityContextFactory<WithMockOseidclientUser>{

	@Override
	public SecurityContext createSecurityContext(WithMockOseidclientUser annotation) {

		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		
		OseidUserDetails principal = new OseidUserDetails(annotation.email());
		Authentication auth = new UsernamePasswordAuthenticationToken(principal, "pass", principal.getAuthorities());
		securityContext.setAuthentication(auth);
		
		return securityContext;
	}

}
