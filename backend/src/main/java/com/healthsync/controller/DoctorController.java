package com.healthsync.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthsync.entity.Doctor;
import com.healthsync.repository.DoctorRepository;
import com.healthsync.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;

	private final DoctorService doctorService;
	
	public DoctorController(DoctorService doctorService, DoctorRepository doctorRepository) {
		this.doctorService = doctorService;
		this.doctorRepository = doctorRepository;
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
	public ResponseEntity<Doctor> createDoctor(@Valid @RequestBody Doctor doctor)
	{
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(doctorService.createDoctor(doctor));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Doctor> updatedDoctor(@PathVariable Long id ,@Valid @RequestBody Doctor doctor)
	{
		return ResponseEntity.ok(doctorService.updateDoctor(id, doctor));
	}

	@DeleteMapping("/{id}")
	public void deleteDoctor(@PathVariable Long id)
	{
		doctorService.deleteDoctor(id);
	}
	
}
