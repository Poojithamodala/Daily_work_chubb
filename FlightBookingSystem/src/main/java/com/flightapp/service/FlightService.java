package com.flightapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightapp.entity.Flight;
import com.flightapp.repository.FlightRepository;

@Service
public class FlightService {
	@Autowired
	private FlightRepository flightRepository;
	
	public void addFlight( Flight flight) {
		flightRepository.save(flight);
	}
	public String deleteFlight( Long flightID) {
		flightRepository.deleteById(flightID);
		return "Flight deleted with id: "+flightID;
	}
	public Flight updateFlight(Long id, Map<String, Object> updates) {

	    Flight f = flightRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Flight not found"));
	    if (updates.containsKey("airline")) {
	        f.setAirline((String) updates.get("airline"));
	    }
	    if (updates.containsKey("fromPlace")) {
	        f.setFromPlace((String) updates.get("fromPlace"));
	    }
	    if (updates.containsKey("toPlace")) {
	        f.setToPlace((String) updates.get("toPlace"));
	    }
	    if (updates.containsKey("departureTime")) {
	        f.setDepartureTime(LocalDateTime.parse((String) updates.get("departureTime")));
	    }
	    if (updates.containsKey("arrivalTime")) {
	        f.setArrivalTime(LocalDateTime.parse((String) updates.get("arrivalTime")));
	    }
	    if (updates.containsKey("price")) {
	        f.setPrice((Integer) updates.get("price"));
	    }
	    if (updates.containsKey("totalSeats")) {
	        f.setTotalSeats((Integer) updates.get("totalSeats"));
	    }
	    if (updates.containsKey("availableSeats")) {
	        f.setAvailableSeats((Integer) updates.get("availableSeats"));
	    }

	    return flightRepository.save(f);
	}
	
	public List<Flight> getAllFlights(){
		return flightRepository.findAll();
	}
	
	public Flight searchFlightById(Long flightID) {
		Flight f=flightRepository.findById(flightID).orElseThrow(()->new RuntimeException("Flight not found"));
		return f;
	}
	public List<Flight> findByFromPlaceAndToPlaceAndDepartureTimeBetween(String fromPlace, String toPlace, LocalDateTime start, LocalDateTime end){
		return flightRepository.findByFromPlaceAndToPlaceAndDepartureTimeBetween(fromPlace, toPlace, start, end);
	}
	public List<Flight> searchFlightsByAirline(String fromPlace,String toPlace, String airline){
	    return flightRepository.findByFromPlaceAndToPlaceAndAirline(fromPlace, toPlace, airline);
    }
}
