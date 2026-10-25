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

import com.healthsync.entity.User;
import com.healthsync.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {


	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getUser()
	{
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id)
	{
		return userService.getUserById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<?> createUser(@Valid @RequestBody User user)
	{
		try
		{
			User createdUser = userService.createUser(user);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(createdUser);
			
	
		}catch (RuntimeException e) {

			if("Email Already Existed".equals(e.getMessage()))
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(e.getMessage());
			
			if("Role Not Found".equals(e.getMessage()))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(e.getMessage());
			
			throw e;
			
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id ,@Valid @RequestBody User user)
	{
		try
		{
			
			User updatedUser = userService.updateUser(id, user);
			
			return ResponseEntity.ok(updatedUser);
			
		}catch (RuntimeException e) {
		
			if("Email Already Existed".equals(e.getMessage()))
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(e.getMessage());
			
			if("Role Not Found".equals(e.getMessage()))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(e.getMessage());
			
			if("User Not Found".equals(e.getMessage()))
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(e.getMessage());
			
			throw e;
			
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id)
	{
		
		try {
			
			userService.deleteUser(id);
			
			return ResponseEntity.noContent().build();
				
		} catch (RuntimeException e) {
			
			if("User Not Found".equals(e.getMessage()))
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(e.getMessage());
		
			throw e;
			
		}
		
		
	}	
	
}
