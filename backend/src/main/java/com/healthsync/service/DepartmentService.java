package com.healthsync.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.healthsync.repository.DepartmentRepository;

import com.healthsync.entity.Department;

@Service
public class DepartmentService {

	private final DepartmentRepository departmentRepository;
	
	public DepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}
	
	public List<Department> getAllDepartments()
	{
		return departmentRepository.findAll();
	}
	
	public Optional<Department> getDepartmentById(Long id)
	{
		return departmentRepository.findById(id); 
	}
	
	public Department createDepartment(Department department)
	{
		return departmentRepository.save(department);
	}
	
	public Department updateDepartment(Long id, Department updatedDepartment)
	{
		Department department = departmentRepository.findById(id)
				.orElseThrow( () -> new RuntimeException("Department Not Found"));	
		
		department.setName(updatedDepartment.getName());
		department.setDescription(updatedDepartment.getDescription());
		
		return departmentRepository.save(department);	
	}
	
	public void deleteDepartment(Long id)
	{
		departmentRepository.deleteById(id);
	}
	
}
