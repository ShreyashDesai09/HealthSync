package com.healthsync.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.healthsync.entity.Doctor;
import com.healthsync.repository.DoctorRepository;

@Service
public class DoctorService {

	private final DoctorRepository doctorRepository;
	
	public DoctorService(DoctorRepository doctorRepository) 
	{
		this.doctorRepository = doctorRepository;
	}
	
	public List<Doctor> getAllDoctors()
	{
		return doctorRepository.findAll();
	}
	
	public Optional<Doctor> getDoctorById(Long id)
	{
		return doctorRepository.findById(id);
	}
	
	public Doctor createDoctor(Doctor doctor)
	{
		return doctorRepository.save(doctor);
	}
	
	public Doctor updateDoctor(Long id , Doctor updateDoctor)
	{
		
		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow( () -> new RuntimeException("Doctor Not Found"));
		
		doctor.setName(updateDoctor.getName());
		doctor.setDepartment(updateDoctor.getDepartment());
		doctor.setEmail(updateDoctor.getEmail());
		doctor.setPhone(updateDoctor.getPhone());
		doctor.setSpecialization(updateDoctor.getSpecialization());
		
		return doctorRepository.save(doctor);
	}
	
	public void deleteDoctor(Long id)
	{
		doctorRepository.deleteById(id);
	}
	
}
