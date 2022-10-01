package com.auth2.oseidclient.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.auth2.oseidclient.user.entity.OseidUserDetails;
import com.auth2.oseidclient.user.repository.OseidUserDetailsRepository;



public class OseidUserDetailsService implements UserDetailsService {

	@Autowired
	private OseidUserDetailsRepository oseidUserDetailsRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		OseidUserDetails loadedOseidUserDetails = oseidUserDetailsRepository.findByEmail(email)
							.orElseThrow(() -> new UsernameNotFoundException(email+" not found"));
		
		if(loadedOseidUserDetails.getEnabled()==true) {
			UserDetails currentUser = User.withUsername(loadedOseidUserDetails.getEmail())
				.password(loadedOseidUserDetails.getPassword())
				.authorities(new SimpleGrantedAuthority(loadedOseidUserDetails.getRoles()))
				.build();
			
			return currentUser;
		}
		
		return new OseidUserDetails("Not_Registered");							
	}

}