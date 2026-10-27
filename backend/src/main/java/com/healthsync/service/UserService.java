package com.healthsync.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthsync.entity.User;
import com.healthsync.repository.RoleRepository;
import com.healthsync.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository , RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}	
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}
	
	public Optional<User> getUserById(Long id)
	{
		return userRepository.findById(id);
	}
	
	public User createUser(User user)
	{
		if(userRepository.existsByEmail(user.getEmail()))
			throw new RuntimeException("Email Already Existed");
		
		if(!roleRepository.existsById(user.getRole().getId()))
			throw new RuntimeException("Role Not Found");
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public User updateUser(Long id , User user)
	{
		
		if(userRepository.existsByEmailAndIdNot(user.getEmail(), id))
			throw new RuntimeException("Email Already Existed");
		
		if (!roleRepository.existsById(user.getRole().getId()))
		    throw new RuntimeException("Role Not Found");
		
		User updatedUser = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User Not Found"));
		
		updatedUser.setName(user.getName());
		updatedUser.setEmail(user.getEmail());
		updatedUser.setRole(user.getRole());
		
		return userRepository.save(updatedUser);
	}
	
	public void deleteUser(Long id)
	{
		if(!userRepository.existsById(id))
			throw new RuntimeException("User Not Found");
		
		userRepository.deleteById(id);
	}
	
	public User authenticateUser(String email , String rawPassword)
	{
		User user = userRepository.findByEmail(email)
				.orElseThrow(()  -> new RuntimeException("Invalid Email or Password"));
		
		if(!passwordEncoder.matches(rawPassword, user.getPassword()))
			throw new RuntimeException("Invalid Email or Password");
		
		return user;
	}
	
}
 