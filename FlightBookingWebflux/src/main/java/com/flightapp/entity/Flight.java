package com.flightapp.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Table("flight")
public class Flight {

	@Id
	private Long id;
	@NotBlank(message = "Airline name cannot be null")
	private String airline;
	@NotBlank(message = "From place cannot be null")
	private String fromPlace;
	@NotBlank(message = "To place cannot be null")
	private String toPlace;
	@NotNull(message = "Departure time cannot be null")
	private LocalDateTime departureTime;
	@NotNull(message = "Arrival time cannot be null")
	private LocalDateTime arrivalTime;
	@Min(value = 1, message = "Price should be atleast 1")
	private int price;
	@Min(value = 1, message = "Seats should be atleast 1")
	private int totalSeats;
	@Min(value = 0, message = "Seats cannot be negative")
	private int availableSeats;

}
