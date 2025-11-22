package com.flightapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightapp.entity.Ticket;
import com.flightapp.entity.User;

public interface TicketRepository extends JpaRepository<Ticket,Long>{
	Optional<Ticket> findByPnr(String pnr);
    List<Ticket> findByUser(User user);
}