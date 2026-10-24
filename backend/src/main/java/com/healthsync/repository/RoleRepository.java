package com.healthsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthsync.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	boolean existsByName(String name);
	
	boolean existsByNameAndIdNot(String name , Long id);
	
}
