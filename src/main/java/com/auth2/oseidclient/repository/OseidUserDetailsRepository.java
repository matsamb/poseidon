package com.auth2.oseidclient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth2.oseidclient.entity.OseidUserDetails;


public interface OseidUserDetailsRepository extends JpaRepository<OseidUserDetails, String>{

	//Optional<OseidUserDetails> findByEmail(String email);
	Optional<OseidUserDetails> findByUsername(String username);
	
}
