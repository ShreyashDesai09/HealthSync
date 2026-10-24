package com.healthsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthsync.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public boolean existsByEmail(String email);
	
	boolean existsByEmailAndIdNot(String email, Long id);	
	
}
