package com.flightapp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightapp.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long>{
	List<Flight> findByFromPlaceAndToPlaceAndDepartureTimeBetween(
	        String fromPlace, String toPlace, LocalDateTime start, LocalDateTime end);
	List<Flight> findByFromPlaceAndToPlaceAndAirline(String fromPlace,String toPlace, String airline);
}