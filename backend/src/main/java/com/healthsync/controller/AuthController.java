package com.healthsync.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthsync.dto.LoginRequest;
import com.healthsync.dto.LoginResponse;
import com.healthsync.entity.User;
import com.healthsync.security.JwtService;
import com.healthsync.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;
	private final JwtService jwtService;
	
	public AuthController(UserService userService , JwtService jwtService) {
		this.userService = userService;
		this.jwtService= jwtService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request)
	{
		
		try {
			
			User user = userService.authenticateUser(request.getEmail(), request.getPassword());
			
			String token = jwtService.generateToken(user.getEmail());
			
			LoginResponse response = new LoginResponse(
			        user.getId(),
			        user.getName(),
			        user.getEmail(),
			        user.getRole().getId(),
			        user.getRole().getName(),
			        token
			);
			
			return ResponseEntity.ok(response);
			
		} catch(RuntimeException e)
		{
		
			if("Invalid Email or Password".equals(e.getMessage()))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(e.getMessage());
			
			throw e;
			
		}
		
	}
	
	
}
