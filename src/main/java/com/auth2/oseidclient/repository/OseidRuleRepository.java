package com.auth2.oseidclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth2.oseidclient.entity.OseidRule;

public interface OseidRuleRepository extends JpaRepository<OseidRule, Integer>{

}
