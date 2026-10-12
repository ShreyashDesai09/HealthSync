package com.healthsync.entity;

import java.text.DateFormat;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "department")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLong;
	
	@NotBlank(message = "Department name is Required")
	@Size(max = 100, message = "Name Cannot Exceed 100 Characters")
	@Column(length = 100)
	private String name;
	
	@Size(max = 500, message = "Description Cannot Exceed 500 Characters")
	@Column(length = 500)
	private String description;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAT;
	
	public Department()
	{	}
	
}
