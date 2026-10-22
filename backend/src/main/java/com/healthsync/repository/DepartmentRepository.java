package com.healthsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthsync.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department , Long>{

	
	
}
