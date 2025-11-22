package com.flightapp.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.entity.Passenger;

public interface PassengerRepository extends ReactiveCrudRepository<Passenger, Long> {
	
}
