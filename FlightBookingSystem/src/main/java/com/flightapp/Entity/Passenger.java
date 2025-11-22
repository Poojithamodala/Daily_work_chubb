package com.flightapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.flightapp.entity.Ticket;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Passenger {
	@Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
  @NotBlank(message="User name is required")
  private String name;
  @NotBlank(message="Gender is required")
  private String gender;
  @NotNull(message="age is required")
  private Integer age;
  @NotBlank(message="seat number is required")
  private String seatNumber;
  private String mealPreference;
  
  @ManyToOne
  @JoinColumn(name = "ticket_id")
  @JsonBackReference
  private Ticket ticket;
	

}
