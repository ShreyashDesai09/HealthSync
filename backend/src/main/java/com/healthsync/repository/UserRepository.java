package com.healthsync.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthsync.entity.User;


public interface UserRepository extends JpaRepository<User, Long>{

	public boolean existsByEmail(String email);
	
	boolean existsByEmailAndIdNot(String email, Long id);	
	
	Optional<User> findByEmail(String email);
	
}
