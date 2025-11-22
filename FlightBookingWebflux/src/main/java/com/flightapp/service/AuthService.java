package com.flightapp.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.flightapp.entity.User;
import com.flightapp.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class AuthService {
	private UserRepository userRepository;

	public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	Map<String, String> loginSessions = new HashMap<>();

	public Mono<User> register(User user) {
		return userRepository.save(user);
	}

	public Mono<String> login(String email, String password) {
		return userRepository.findByEmail(email).switchIfEmpty(Mono.error(new RuntimeException("User not found")))
				.flatMap(user -> {
					if (user == null || !user.getPassword().equals(password))
						return Mono.error(new RuntimeException("Invalid password"));
					String sessionID = UUID.randomUUID().toString();
					loginSessions.put(sessionID, email);
					return Mono.just(sessionID);
				});
	}

	public Mono<User> getloggedInUser(String sessionID) {
		String userEmail = loginSessions.get(sessionID);
		if (userEmail == null)
			return Mono.error(new RuntimeException("Invalid session ID"));
		return userRepository.findByEmail(userEmail).switchIfEmpty(Mono.error(new RuntimeException("User not found")));
	}

}
