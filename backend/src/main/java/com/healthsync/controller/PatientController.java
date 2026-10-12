package com.healthsync.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.healthsync.entity.Patient;
import com.healthsync.service.PatientService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/patients")
public class PatientController {

	private final PatientService patientService;
	
	public PatientController(PatientService patientService)
	{
		this.patientService = patientService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable Long id)
	{
		return patientService.getPatientById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping
	public ResponseEntity<List<Patient>> getAllPatients()
	{
		return ResponseEntity.ok(patientService.getAllPatients());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Long id,
			@Valid @RequestBody Patient patient
			)
	{
		Patient updatePatient = patientService.updatePatient(id, patient);
		
		return ResponseEntity.ok(updatePatient);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePatient(@PathVariable Long id)
	{
		patientService.deletePatient(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient)
	{
		Patient savedPatient = patientService.savePatient(patient);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
	}

}
