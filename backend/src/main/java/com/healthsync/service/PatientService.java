package com.healthsync.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.healthsync.entity.Patient;

import com.healthsync.repository.PatientRepository;


@Service
public class PatientService {

	private final PatientRepository patientRepository;
	
	public PatientService(PatientRepository patientRepository)
	{
		this.patientRepository = patientRepository;
	}
	
	public List<Patient> getAllPatients()
	{
		return patientRepository.findAll();
	}
	
	public Optional<Patient> getPatientById(Long id)
	{
		return patientRepository.findById(id);
	}
	
	public Patient savePatient(Patient patient)
	{
		return patientRepository.save(patient);
	}
	
	public void deletePatient(Long id)
	{
		patientRepository.deleteById(id);
	}
	
	public Patient updatePatient(Long id, Patient updatedPatient)
	{
		Patient existingPatient = patientRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
		
		existingPatient.setFirstName(updatedPatient.getFirstName());
	    existingPatient.setMiddleName(updatedPatient.getMiddleName());
	    existingPatient.setLastName(updatedPatient.getLastName());
	    existingPatient.setDateOfBirth(updatedPatient.getDateOfBirth());
	    existingPatient.setGender(updatedPatient.getGender());
	    existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
	    existingPatient.setEmail(updatedPatient.getEmail());
	    existingPatient.setAddress(updatedPatient.getAddress());

	    return patientRepository.save(existingPatient);
	}
	
}
