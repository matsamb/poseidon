package com.auth2.oseidclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth2.oseidclient.entity.CurvePoint;

public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
