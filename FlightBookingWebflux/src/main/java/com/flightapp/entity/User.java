package com.flightapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Table("user")
public class User {
	@Id
	private Long id;
	@NotBlank(message = "Name cannot be null")
	private String name;
	@NotBlank(message = "Email cannot be null")
	private String email;
	@NotNull(message = "Age cannot be null")
	private int age;
	@NotBlank(message = "Gender cannot be null")
	private String gender;
	private String password;
	private Role role;

}
