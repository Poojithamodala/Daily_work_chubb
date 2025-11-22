package com.flightapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightapp.entity.Passenger;
import com.flightapp.repository.PassengerRepository;

@Service
public class PassengerService {
	@Autowired
	private PassengerRepository passengerRepository;
	public List<Passenger> getAllPassengers(){
		return passengerRepository.findAll();
	}
}
