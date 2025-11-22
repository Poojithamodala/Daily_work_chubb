package com.flightapp.controller;

import com.flightapp.entity.Flight;
import com.flightapp.entity.User;
import com.flightapp.service.AuthService;
import com.flightapp.service.FlightService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/flight/airline")
public class AdminController {
	@Autowired
    private final AuthService authService;
	@Autowired
    private final FlightService flightService;

    public AdminController(AuthService authService, FlightService flightService) {
        this.authService = authService;
        this.flightService = flightService;
    }
    
    @PostMapping("/admin/login")
    public String adminLogin(@RequestBody User user) {
        String session = authService.login(user.getEmail(), user.getPassword());
        if (session == null) return "Invalid credentials";
        return session;
    }

    @PostMapping("/inventory/add")
    public String addFlight(@RequestBody Flight flight) {
        flightService.addFlight(flight);
        return "Flight added successfully";
    }

    @PutMapping("/inventory/update/{id}")
    public Flight update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return flightService.updateFlight(id, updates);
    }

    @DeleteMapping("/inventory/delete/{id}")
    public String delete(@PathVariable Long id) {
        return flightService.deleteFlight(id);
    }
}