package com.auth2.oseidclient.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
public class Factory {

	@Bean
	public PasswordEncoder passwordEncoder() {
		String currentEncoder = "Bcrypt";

		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(currentEncoder, new BCryptPasswordEncoder());

		return encoders.get(currentEncoder);

	}
	
}
