package com.auth2.oseidclient;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.repository.OseidUserDetailsRepository;
import com.auth2.oseidclient.entity.OseidUserDetails;

@SpringBootApplication
@RestController
public class OseidclientApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(OseidclientApplication.class, args);
	}

	@RolesAllowed({"ROLE_USER","ROLE_ADMIN","ROLE_MANAGERS","ROLE_DEVELOPERS"})
	@GetMapping("/home")
	public String home() {
		return "Welcome to OSEID Application";
		
	}
	
	@GetMapping("/forbidden")
	public String denied() {
		return "HTTP SATUS 403, ACCESS DENIED";
		
	}
	
	@Autowired
	private OseidUserDetailsRepository oseidUserDetailsRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		OseidUserDetails admin = new OseidUserDetails("admin@oseid.com");
		
		String adminpass = passwordEncoder.encode("admin");
		System.out.println(adminpass);
		admin.setPassword(adminpass);
		admin.setEnabled(true);
		admin.setLocked(false);
		admin.setRoles("ROLE_ADMIN");
		 
		OseidUserDetails user = new OseidUserDetails("user@oseid.com");
		String userpass = passwordEncoder.encode("user");
		System.out.println(userpass);
		user.setPassword(userpass);
		user.setEnabled(true);
		user.setLocked(false);
		user.setRoles("ROLE_USER");

		
		oseidUserDetailsRepository.save(admin);
		oseidUserDetailsRepository.save(user);
	}

}
