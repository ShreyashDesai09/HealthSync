package com.healthsync.controller;

import com.healthsync.entity.Department;
import com.healthsync.repository.DepartmentRepository;
import com.healthsync.service.DepartmentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

	private final DepartmentService departmentService;
	
	public DepartmentController(DepartmentService departmentService, DepartmentRepository departmentRepository)
	{
		this.departmentService = departmentService;
		this.departmentRepository = departmentRepository;
	}
	
	@GetMapping
	public ResponseEntity<List<Department>> departments() {
		return ResponseEntity.ok(departmentService.getAllDepartments());
	}	
	
	@GetMapping("/{id}")
	public ResponseEntity<Department> getDepartment(@PathVariable Long id)
	{	
	    return departmentService.getDepartmentById(id)
	            .map(department -> ResponseEntity.ok(department))
	            .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department)
	{
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(departmentService.createDepartment(department));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable Long id ,@Valid @RequestBody Department department)
	{
		return ResponseEntity.ok(departmentService.updateDepartment(id , department));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDepartment(@PathVariable Long id)
	{
		departmentService.deleteDepartment(id);
		return ResponseEntity.noContent().build();
	}
	
}
