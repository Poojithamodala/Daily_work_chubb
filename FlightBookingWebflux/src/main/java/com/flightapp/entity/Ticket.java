package com.flightapp.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("ticket")
public class Ticket {
	@Id
	private Long id;
	private String pnr;
	private Long userId;
	private Long departureFlightId;
	private Long returnFlightId;
	private FlightType tripType;
	private String seatsBooked;
	private Double totalPrice;
	private LocalDateTime bookingTime;
	private boolean cancel;

}
