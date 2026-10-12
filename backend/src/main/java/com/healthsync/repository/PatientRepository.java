package com.healthsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthsync.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

}