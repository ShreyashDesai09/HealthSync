package com.healthsync.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthsync.entity.Role;
import com.healthsync.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

	private final RoleService roleService;
	
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@GetMapping
	public ResponseEntity<List<Role>> getAllRoles()
	{
		return ResponseEntity.ok(roleService.getAllRoles());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable Long id)
	{
		return roleService.getRoleById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<?> createRole(@Valid @RequestBody Role role)
	{
		try {
			
		Role createdRole = roleService.createRole(role);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(createdRole);
		
		} catch (RuntimeException e) {

			if("Role Already Exists".equals(e.getMessage()))
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(e.getMessage());
			
			throw e;
		}
	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateRole(@PathVariable Long id , @Valid @RequestBody Role role)
	{
		try {		
			Role updatedRole = roleService.updateRole(id, role);
			return ResponseEntity.ok(updatedRole);		
		} catch (RuntimeException e) {
			if ("Role Already Exists".equals(e.getMessage())) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(e.getMessage());
			}
			if("No Such Role Exists".equals(e.getMessage()))
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(e.getMessage());
			throw e;
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRole(@PathVariable Long id)
	{
		
		try {
			roleService.deleteRole(id);

			return ResponseEntity.noContent().build();
			
		} catch (RuntimeException e) {

			if("No Role Exists".equals(e.getMessage()))
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(e.getMessage());
			}
			
			throw e;
			
		}
		
		
		
		
	}
	
	
	
	
}
