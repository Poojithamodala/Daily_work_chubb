package com.flightapp.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.flightapp.entity.Ticket;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Flight {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message="Airline name cannot be null")
	private String airline;
	@NotBlank(message="From place cannot be null")
	private String fromPlace;
	@NotBlank(message="To place cannot be null")
	private String toPlace;
	@NotNull(message="Departure time cannot be null")
	private LocalDateTime departureTime;
	@NotNull(message="Arrival time cannot be null")
	private LocalDateTime arrivalTime;
	@Min(value=1, message="Price should be atleast 1")
	private int price;
	@Min(value=1, message="Seats should be atleast 1")
	private int totalSeats;
	@Min(value=0, message="Seats cannot be negative")
	private int availableSeats;
	
	@OneToMany
	@JsonManagedReference("departure-flight")
    private List<Ticket> departureTickets=new ArrayList<>();

    @OneToMany
    @JsonManagedReference("return-flight")
    private List<Ticket> returnTickets=new ArrayList<>();
}
