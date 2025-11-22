package com.flightapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Table("passenger")
public class Passenger {
	@Id
	private Long id;
	@NotBlank(message = "User name is required")
	private String name;
	@NotBlank(message = "Gender is required")
	private String gender;
	@NotNull(message = "age is required")
	private Integer age;
	@NotBlank(message = "seat number is required")
	private String seatNumber;
	private String mealPreference;
	private Long ticketId;

}
