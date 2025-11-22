package com.flightapp.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightapp.entity.User;
import com.flightapp.repository.UserRepository;

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepository;
	
	public User register(User user) {
	    return userRepository.save(user);
	}
	
	Map<String, String> loginSessions=new HashMap<>();
	public String login( String email, String password) {
		User user=userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
		if(user==null||!user.getPassword().equals(password))
			return null;
		String sessionID=UUID.randomUUID().toString();
		loginSessions.put(sessionID,email);
		return sessionID;
	}
	
	public User getloggedInUser( String sessionID) {
		String userEmail=loginSessions.get(sessionID);
		if(userEmail==null)
			return null;
		return userRepository.findByEmail(userEmail).orElseThrow(()-> new RuntimeException("User not found"));
	}

}
