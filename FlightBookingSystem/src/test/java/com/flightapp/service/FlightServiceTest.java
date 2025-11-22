package com.flightapp.service;

import static org.junit.jupiter.api.Assertions.*;

import com.flightapp.entity.Flight;
import org.junit.jupiter.api.Test;

public class FlightServiceTest {

    @Test
    void testUpdateFlightAirline() {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setAirline("Indigo");
        flight.setAirline("Air India");

        assertEquals("Air India", flight.getAirline(), "Airline should be updated");
    }
}