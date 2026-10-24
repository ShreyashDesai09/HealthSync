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

import com.healthsync.entity.Doctor;
import com.healthsync.repository.DepartmentRepository;
import com.healthsync.repository.DoctorRepository;
import com.healthsync.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DepartmentRepository departmentRepository;

    private final DoctorService doctorService;
	
	public DoctorController(DoctorService doctorService, DoctorRepository doctorRepository, DepartmentRepository departmentRepository) {
		this.doctorService = doctorService;
		this.departmentRepository = departmentRepository;
	}
	
	@GetMapping
	public List<Doctor> getDoctors()
	{
		return doctorService.getAllDoctors();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id)
	{
		return doctorService.getDoctorById(id)
				.map(doctor -> ResponseEntity.ok(doctor))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<?> createDoctor(@Valid @RequestBody Doctor doctor)
	{
		try {
			Doctor createdDoctor = doctorService.createDoctor(doctor);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(createdDoctor);
			
		}catch(RuntimeException e)
		{
			if("Email Already Existed".equals(e.getMessage()))
			{
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(e.getMessage());
			}
			
			if ("Department Not found".equals(e.getMessage())) {
			    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			            .body(e.getMessage());
			}
			
			throw e;
		}
		
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updatedDoctor(
	        @PathVariable Long id,
	        @Valid @RequestBody Doctor doctor) {

	    try {
	        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
	        return ResponseEntity.ok(updatedDoctor);

	    } catch (RuntimeException e) {
	        if ("Doctor Not Found".equals(e.getMessage())) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(e.getMessage());
	        }

	        throw e;
	    }
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
	    try {
	        doctorService.deleteDoctor(id);
	        return ResponseEntity.noContent().build();

	    } catch (RuntimeException e) {
	        if ("Doctor Not Found".equals(e.getMessage())) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(e.getMessage());
	        }

	        throw e;
	    }
	}
	
}
