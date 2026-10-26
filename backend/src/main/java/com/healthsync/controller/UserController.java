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
import com.healthsync.dto.UserResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {


	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	private UserResponse toUserResponse(User user)
	{
		return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole().getId(), user.getRole().getName(), user.getCreatedAt(), user.getUpdatedAt());
	}
	
	@GetMapping
	public ResponseEntity<List<UserResponse>> getUser()
	{
		
		List<UserResponse> users = userService.getAllUsers().stream().map(this::toUserResponse).toList();
				
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id)
	{	
		return userService.getUserById(id)
				.map(this::toUserResponse)
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
					.body(toUserResponse(createdUser));
			
	
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
			
			return ResponseEntity.ok(toUserResponse(updatedUser));
			
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
