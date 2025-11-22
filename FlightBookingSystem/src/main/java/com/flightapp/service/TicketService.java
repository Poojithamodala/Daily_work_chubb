package com.flightapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightapp.entity.Flight;
import com.flightapp.entity.FlightType;
import com.flightapp.entity.Passenger;
import com.flightapp.entity.Ticket;
import com.flightapp.entity.User;
import com.flightapp.repository.FlightRepository;
import com.flightapp.repository.TicketRepository;
import com.flightapp.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TicketService {
	@Autowired
	private FlightRepository flightRepository;
	
    @Autowired
    private UserRepository userRepository;
	@Autowired
	private TicketRepository ticketRepository;
	   public String bookTicket(Long userId,Long departureFlightId,Long returnFlightId,List<Passenger> passengers,FlightType tripType) {
		User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found"));
		Flight departureFlight = flightRepository.findById(departureFlightId).orElseThrow(() -> new RuntimeException("Departure flight not found"));
		Flight returnFlight = null;
		int seatCount = passengers.size();
		
		if (tripType == FlightType.ROUND_TRIP) {
            returnFlight = flightRepository.findById(returnFlightId)
                    .orElseThrow(() -> new RuntimeException("Return flight not found"));
            if (returnFlight.getAvailableSeats() < seatCount)
                throw new RuntimeException("Not enough seats in return flight");
        }
		if (departureFlight.getAvailableSeats() < seatCount)
            throw new RuntimeException("no enough seats for departure flight");
		 departureFlight.setAvailableSeats(departureFlight.getAvailableSeats() - seatCount);
	        flightRepository.save(departureFlight);
	        if (returnFlight != null) {
	            returnFlight.setAvailableSeats(returnFlight.getAvailableSeats() - seatCount);
	            flightRepository.save(returnFlight);
	        } 
	        Ticket ticket = new Ticket();
	        ticket.setUser(user);
	        ticket.setDepartureFlight(departureFlight);
	        ticket.setReturnFlight(returnFlight);
	        ticket.setTripType(tripType);

	        ticket.setBookingTime(LocalDateTime.now());
	        ticket.setPnr(UUID.randomUUID().toString().substring(0, 8));

	        double total = departureFlight.getPrice() * seatCount;
	        if (returnFlight != null) total += returnFlight.getPrice() * seatCount;

	        ticket.setTotalPrice(total);
	        
	        for (Passenger p : passengers) {
	            p.setTicket(ticket);
	        }
	        ticket.setPassengers(passengers);

	        ticketRepository.save(ticket);

	        return ticket.getPnr();
		}
	   public List<Ticket> getHistory(String email) {
	        User user = userRepository.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        return ticketRepository.findByUser(user);
	    }
	   public String cancelTicket(String pnr, String email) {

	        Ticket ticket = ticketRepository.findByPnr(pnr).orElseThrow(()->new RuntimeException("no flight with this pnr"));
	        if (ticket == null) return "Invalid PNR";

	        if (!ticket.getUser().getEmail().equals(email))
	            return "You cannot cancel another users ticket";
	        if (ticket.isCancel())
	            return "ticket got already cancelled";
	        LocalDateTime departureTime = ticket.getDepartureFlight().getDepartureTime();

	        if (LocalDateTime.now().plusHours(24).isAfter(departureTime))
	            return "Cannot cancel within 24 hours of travel";

	        int seats = ticket.getPassengers().size();
	        Flight dep = ticket.getDepartureFlight();
	        dep.setAvailableSeats(dep.getAvailableSeats() + seats);
	        flightRepository.save(dep);

	        if (ticket.getReturnFlight() != null) {
	            Flight ret = ticket.getReturnFlight();
	            ret.setAvailableSeats(ret.getAvailableSeats() + seats);
	            flightRepository.save(ret);
	        }
	        ticket.setCancel(true);
	        ticketRepository.save(ticket);
	        return "Cancelled Successfully";
	   }
	   public Ticket getTicketByPnr(String pnr) {
		    return ticketRepository.findByPnr(pnr)
		        .orElseThrow(() -> new RuntimeException("No ticket found with this PNR"));
		}
}