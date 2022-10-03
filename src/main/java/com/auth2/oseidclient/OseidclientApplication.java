package com.auth2.oseidclient;

import java.sql.Timestamp;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.bid.entity.Bid;
import com.auth2.oseidclient.bid.repository.BidRepository;
import com.auth2.oseidclient.user.entity.OseidUserDetails;
import com.auth2.oseidclient.user.repository.OseidUserDetailsRepository;

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
	private BidRepository bidRepository;
	
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
		
		Bid bid = new Bid("busta");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		bid.setCreationDate(time);
		bid.setCommentary("YeYo");
		
		bidRepository.save(bid);
	}

}
