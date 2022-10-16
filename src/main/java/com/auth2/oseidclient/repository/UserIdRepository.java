package com.auth2.oseidclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth2.oseidclient.entity.UserId;

public interface UserIdRepository extends JpaRepository<UserId, Integer>{

}
