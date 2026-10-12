package com.healthsync.entity;

import java.time.LocalDate;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "patients")
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "First Name is Required")
	@Size(max = 100, message = "Name cannot exceed 100 characters")
	@Column(nullable = false, length = 100)
	private String firstName;

	@Size(max = 100, message = "Middle name cannot exceed 100 characters")
	@Column(length = 100)
	private String middleName;
	
	@NotBlank(message = "Last Name is Reqeuired")
	@Size(max = 100, message = "Name cannot exceed 100 characters")
	@Column(nullable = false, length = 100)
	private String lastName;
	
	@NotNull(message = "Date of Birth Required")
	@PastOrPresent(message = "Birth Date Cannot be in Future")
	@Column(nullable = false)
	private LocalDate dateOfBirth;
	
	@Column(length = 20)
	private String gender;
	
	@Column(length = 20)
	private String phoneNumber;

	@Email(message = "Provide Valid Email Address")
	@Column(length = 150)
	private String email;

	@Column(length = 500)
	private String address;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	public Patient()
	{	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	@PrePersist
	protected void onCreate() {
	    createdAt = LocalDateTime.now();
	    updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
	    updatedAt = LocalDateTime.now();
	}
	
}
