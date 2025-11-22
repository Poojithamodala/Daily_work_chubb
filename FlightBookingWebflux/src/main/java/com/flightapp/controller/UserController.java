package com.flightapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.entity.Flight;
import com.flightapp.entity.FlightType;
import com.flightapp.entity.Passenger;
import com.flightapp.entity.Ticket;
import com.flightapp.entity.User;
import com.flightapp.service.AuthService;
import com.flightapp.service.FlightService;
import com.flightapp.service.TicketService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1.0/flight")
public class UserController {
	private final AuthService authService;
    private final FlightService flightService;
    private final TicketService ticketService;

    public UserController(AuthService authService, FlightService flightService, TicketService ticketService) {
        this.authService = authService;
        this.flightService = flightService;
        this.ticketService = ticketService;
    }
    
    @PostMapping("/user/register")
    public Mono<User> register(@RequestBody User user) {
        return authService.register(user);
    }
    
    @PostMapping("/user/login")
    public Mono<String> userLogin(@RequestBody User user) {
        return authService.login(user.getEmail(), user.getPassword())
                .switchIfEmpty(Mono.just("Invalid credentials"));
    }
    
    @PostMapping("/search")
    public Flux<Flight> searchFlights(@RequestBody Flight f) {
        return flightService.findByFromPlaceAndToPlaceAndDepartureTimeBetween(
                f.getFromPlace(),
                f.getToPlace(),
                f.getDepartureTime(),
                f.getArrivalTime()
        );
    }
    
    @PostMapping("/search/airline")
    public Flux<Flight> searchByAirline(@RequestBody Map<String, String> body) {
        return flightService.searchFlightsByAirline(
                body.get("fromPlace"),
                body.get("toPlace"),
                body.get("airline")
        );
    }
    
    @GetMapping("/allflights")
    public Flux<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }
    
    @PostMapping("/booking")
    public Mono<String> bookTicket(@RequestBody Map<String, Object> requestBody) {

        Long userId = Long.parseLong(requestBody.get("userId").toString());
        Long departureFlightId = Long.parseLong(requestBody.get("departureFlightId").toString());
        FlightType tripType = FlightType.valueOf(requestBody.get("tripType").toString());

        Long returnFlightId = requestBody.get("returnFlightId") != null
                ? Long.parseLong(requestBody.get("returnFlightId").toString())
                : null;

        List<Passenger> passengers = new ArrayList<>();
        List<Map<String, Object>> passengerList =
                (List<Map<String, Object>>) requestBody.get("passengers");

        for (Map<String, Object> p : passengerList) {
            Passenger passenger = new Passenger();
            passenger.setName((String) p.get("name"));
            passenger.setAge((Integer) p.get("age"));
            passenger.setGender((String) p.get("gender"));
            passenger.setSeatNumber((String) p.get("seatNumber"));
            passenger.setMealPreference((String) p.getOrDefault("mealPreference", null));
            passengers.add(passenger);
        }

        return ticketService.bookTicket(userId, departureFlightId, returnFlightId, passengers, tripType);
    }
    
    @GetMapping("/ticket/{pnr}")
    public Mono<Ticket> getTicket(@PathVariable String pnr) {
        return ticketService.getTicketByPnr(pnr);
    }
    
    @GetMapping("/booking/history/{email}")
    public Flux<Ticket> history(@PathVariable String email) {
        return ticketService.getHistory(email);
    }
    
    @DeleteMapping("/booking/cancel/{pnr}")
    public Mono<String> cancel(@PathVariable String pnr, @RequestBody Map<String, String> body) {
        return ticketService.cancelTicket(pnr, body.get("email"));
    }

}
