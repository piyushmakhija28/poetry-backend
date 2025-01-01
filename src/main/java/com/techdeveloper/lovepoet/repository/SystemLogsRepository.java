package com.techdeveloper.lovepoet.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techdeveloper.lovepoet.entity.SystemLogs;

public interface SystemLogsRepository extends JpaRepository<SystemLogs, Serializable>{

}
