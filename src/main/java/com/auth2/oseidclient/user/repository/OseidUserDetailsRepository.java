package com.auth2.oseidclient.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth2.oseidclient.user.entity.OseidUserDetails;


public interface OseidUserDetailsRepository extends JpaRepository<OseidUserDetails, String>{

	Optional<OseidUserDetails> findByEmail(String email);
	
}
