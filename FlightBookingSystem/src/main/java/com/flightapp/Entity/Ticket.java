package com.flightapp.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.flightapp.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Ticket {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String pnr;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn
    @JsonBackReference("departure-flight")
    private Flight departureFlight;

    @ManyToOne
    @JoinColumn
    @JsonBackReference("return-flight")
    private Flight returnFlight;
    
    @Enumerated(EnumType.STRING)
	private FlightType tripType;
    
	private String seatsBooked;
	private Double totalPrice;
	private LocalDateTime bookingTime;
	private boolean cancel;
	
	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Passenger> passengers=new ArrayList<>();

}
