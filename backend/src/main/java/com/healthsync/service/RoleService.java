package com.healthsync.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.healthsync.entity.Role;
import com.healthsync.repository.RoleRepository;

@Service
public class RoleService {

	private final RoleRepository roleRepository;
	
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public List<Role> getAllRoles()
	{
		return roleRepository.findAll();
	}
	
	public Optional<Role> getRoleById(Long id)
	{
		return roleRepository.findById(id);
	}
	
	public Role createRole(Role role)
	{
		
		if(roleRepository.existsByName(role.getName()))
			throw new RuntimeException("Role Already Exists");
		
		return roleRepository.save(role);	 
		
	}
	
	public void updateRole(Long id, Role updatedRole)
	{
		
		if(roleRepository.existsByNameAndIdNot(updatedRole.getName(), id))
			throw new RuntimeException("Role Already Exists");
		
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No Such Role Exists"));
		
		role.setName(updatedRole.getName());
		role.setDescription(updatedRole.getDescription());
		
		roleRepository.save(role);
			
	}
	
	public void deleteRole(Long id)
	{
		
		if(!roleRepository.existsById(id))
			throw new RuntimeException("No Role Exists");
		
		roleRepository.deleteById(id);
		
	}
	
}
