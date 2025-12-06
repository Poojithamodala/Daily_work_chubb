package com.springjwt.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	@NotBlank
	@Size(max=20)
	@Column(unique = true)
	String username;
	
	@NotBlank
	@Size(max=50)
	@Column(unique = true)
	String email;
	
	@NotBlank
	@Size(max=100)
	@Column(length = 100)
	String password;
	
	@Enumerated(EnumType.STRING)
	ERole role;
	
	public User() {
    }
	
	public User(String username, String email, String password, ERole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
