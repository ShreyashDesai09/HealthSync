package com.healthsync.entity;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "doctor")
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 100)
	@Column(nullable = false , length = 100)	
	private String name;
	
	@NotBlank
	@Size(max = 100)
	@Column(nullable = false , length = 100)	
	private String specialization;
	
	@NotBlank
	@Email
	@Column(nullable = false , unique = true)	
	private String email;
	
	@NotBlank
	@Size(max = 20)
	@Column(nullable = false , length = 20)	
	private String phone;
	
	@Column(nullable = false , updatable = false)
	private LocalDateTime createdAt;	
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "department_id" , nullable = false)
	private Department department;
	
	public Doctor()
	{	}
	
	public Doctor( String name ,String specialization, String email, String phone,Department department) 
	{
		this.name = name;
		this.specialization = specialization;
		this.email = email;
		this.phone = phone;
		this.department = department;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@PrePersist
	public void onCreate()
	{
		LocalDateTime now = LocalDateTime.now(); 
		this.createdAt = now;
		this.updatedAt = now;		
	}
	
	@PreUpdate
	public void onUpdate()
	{
		LocalDateTime now = LocalDateTime.now();
		this.updatedAt = now;
		
	}
	
}
