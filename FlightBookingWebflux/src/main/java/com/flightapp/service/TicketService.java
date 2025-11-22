package com.flightapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.flightapp.entity.Flight;
import com.flightapp.entity.FlightType;
import com.flightapp.entity.Passenger;
import com.flightapp.entity.Ticket;
import com.flightapp.repository.FlightRepository;
import com.flightapp.repository.TicketRepository;
import com.flightapp.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TicketService {
	private FlightRepository flightRepository;
	private UserRepository userRepository;
	private TicketRepository ticketRepository;

	public TicketService(FlightRepository flightRepository, UserRepository userRepository,
			TicketRepository ticketRepository) {
		this.flightRepository = flightRepository;
		this.userRepository = userRepository;
		this.ticketRepository = ticketRepository;
	}

	public Mono<String> bookTicket(Long userId, Long departureFlightId, Long returnFlightId, List<Passenger> passengers,
			FlightType tripType) {

		int seatCount = passengers.size();

		return userRepository.findById(userId).switchIfEmpty(Mono.error(new RuntimeException("User not found")))
				.flatMap(user -> flightRepository.findById(departureFlightId)
						.switchIfEmpty(Mono.error(new RuntimeException("Departure flight not found")))
						.flatMap(depFlight -> {

							Mono<Flight> returnFlightMono = tripType == FlightType.ROUND_TRIP
									? flightRepository.findById(returnFlightId)
											.switchIfEmpty(Mono.error(new RuntimeException("Return flight not found")))
									: Mono.just(null);

							return returnFlightMono.flatMap(retFlight -> {

								if (depFlight.getAvailableSeats() < seatCount)
									return Mono.error(new RuntimeException("Not enough seats in departure flight"));

								if (retFlight != null && retFlight.getAvailableSeats() < seatCount)
									return Mono.error(new RuntimeException("Not enough seats in return flight"));

								depFlight.setAvailableSeats(depFlight.getAvailableSeats() - seatCount);
								Mono<Flight> saveDep = flightRepository.save(depFlight);

								Mono<Flight> saveRet = Mono.empty();
								if (retFlight != null) {
									retFlight.setAvailableSeats(retFlight.getAvailableSeats() - seatCount);
									saveRet = flightRepository.save(retFlight);
								}

								Ticket ticket = new Ticket();
								ticket.setUserId(userId);
								ticket.setDepartureFlightId(depFlight.getId());
								ticket.setReturnFlightId(retFlight != null ? retFlight.getId() : null);
								ticket.setTripType(tripType);
								ticket.setPnr(UUID.randomUUID().toString().substring(0, 8));
								ticket.setBookingTime(LocalDateTime.now());

								double total = depFlight.getPrice() * seatCount;
								if (retFlight != null)
									total += retFlight.getPrice() * seatCount;

								ticket.setTotalPrice(total);

								return Mono.when(saveDep, saveRet).then(ticketRepository.save(ticket))
										.map(Ticket::getPnr);
							});
						}));
	}

	public Flux<Ticket> getHistory(String email) {
		return userRepository.findByEmail(email).switchIfEmpty(Mono.error(new RuntimeException("User not found")))
				.flatMapMany(user -> ticketRepository.findByUserId(user.getId()));
	}

	public Mono<Ticket> getTicketByPnr(String pnr) {
		return ticketRepository.findByPnr(pnr).switchIfEmpty(Mono.error(new RuntimeException("No ticket found")));
	}

	public Mono<String> cancelTicket(String pnr, String email) {

		return ticketRepository.findByPnr(pnr).switchIfEmpty(Mono.error(new RuntimeException("PNR not found")))
				.flatMap(ticket -> userRepository.findById(ticket.getUserId()).flatMap(user -> {

					if (!user.getEmail().equals(email))
						return Mono.just("You cannot cancel another user's ticket");

					if (ticket.isCancel())
						return Mono.just("Ticket already cancelled");

					return flightRepository.findById(ticket.getDepartureFlightId()).flatMap(dep -> {

						if (LocalDateTime.now().plusHours(24).isAfter(dep.getDepartureTime()))
							return Mono.just("Cannot cancel within 24 hours");

						int seatCount = 1;

						dep.setAvailableSeats(dep.getAvailableSeats() + seatCount);
						Mono<Flight> saveDep = flightRepository.save(dep);

						Mono<Flight> saveRet = Mono.empty();

						if (ticket.getReturnFlightId() != null) {
							saveRet = flightRepository.findById(ticket.getReturnFlightId()).flatMap(ret -> {
								ret.setAvailableSeats(ret.getAvailableSeats() + seatCount);
								return flightRepository.save(ret);
							});
						}

						ticket.setCancel(true);
						Mono<Ticket> saveTicket = ticketRepository.save(ticket);

						return Mono.when(saveDep, saveRet, saveTicket).then(Mono.just("Cancelled Successfully"));
					});
				}));
	}
}