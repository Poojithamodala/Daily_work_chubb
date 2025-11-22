package com.flightapp.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.flightapp.entity.Flight;
import com.flightapp.repository.FlightRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FlightService {

	private final FlightRepository flightRepository;

	public FlightService(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public Mono<Flight> addFlight(Flight flight) {
		return flightRepository.save(flight);
	}

	public Mono<Void> deleteFlight(Long flightID) {
		return flightRepository.deleteById(flightID);
//		return "Flight deleted with id: "+ flightID;
	}

	public Mono<Flight> updateFlight(Long id, Map<String, Object> updates) {

		return flightRepository.findById(id).switchIfEmpty(Mono.error(new RuntimeException("Flight not found")))
				.flatMap(flight -> {
					if (updates.containsKey("airline")) {
						flight.setAirline((String) updates.get("airline"));
					}
					if (updates.containsKey("fromPlace")) {
						flight.setFromPlace((String) updates.get("fromPlace"));
					}
					if (updates.containsKey("toPlace")) {
						flight.setToPlace((String) updates.get("toPlace"));
					}
					if (updates.containsKey("departureTime")) {
						flight.setDepartureTime(LocalDateTime.parse((String) updates.get("departureTime")));
					}
					if (updates.containsKey("arrivalTime")) {
						flight.setArrivalTime(LocalDateTime.parse((String) updates.get("arrivalTime")));
					}
					if (updates.containsKey("price")) {
						flight.setPrice((Integer) updates.get("price"));
					}
					if (updates.containsKey("totalSeats")) {
						flight.setTotalSeats((Integer) updates.get("totalSeats"));
					}
					if (updates.containsKey("availableSeats")) {
						flight.setAvailableSeats((Integer) updates.get("availableSeats"));
					}

					return flightRepository.save(flight);
				});
	}

	public Flux<Flight> getAllFlights() {
		return flightRepository.findAll();
	}

	public Mono<Flight> searchFlightById(Long flightID) {
		return flightRepository.findById(flightID).switchIfEmpty(Mono.error(new RuntimeException("Flight not found")));
	}

	public Flux<Flight> findByFromPlaceAndToPlaceAndDepartureTimeBetween(String fromPlace, String toPlace,
			LocalDateTime start, LocalDateTime end) {
		return flightRepository.findByFromPlaceAndToPlaceAndDepartureTimeBetween(fromPlace, toPlace, start, end);
	}

	public Flux<Flight> searchFlightsByAirline(String fromPlace, String toPlace, String airline) {
		return flightRepository.findByFromPlaceAndToPlaceAndAirline(fromPlace, toPlace, airline);
	}
}
