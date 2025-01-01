package com.techdeveloper.lovepoet.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techdeveloper.lovepoet.entity.SessionLog;

public interface SessionLogRepository extends JpaRepository<SessionLog, Serializable> {

}
