package com.healthsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthsync.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor,Long>{

	boolean existsByEmail(String email);
	
}
