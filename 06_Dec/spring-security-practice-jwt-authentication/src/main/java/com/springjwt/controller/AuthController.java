package com.springjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springjwt.models.ERole;
import com.springjwt.models.User;
import com.springjwt.repository.UserRepository;
import com.springjwt.request.LoginRequest;
import com.springjwt.request.SignUpRequest;
import com.springjwt.response.JwtResponse;
import com.springjwt.service.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtils;
    
//    @PostMapping("/signin")
//    public JwtResponse authenticateUser(@RequestBody LoginRequest request) {
//    	var authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//    	var user = userRepository.findByUsername(request.getUsername()).get();
//    	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//    	String jwt = jwtUtils.generateJwtToken(userDetails);
//    	return new JwtResponse(jwt, user.getUsername(), user.getEmail(), user.getRole().name());
//    }
    @PostMapping("/signin")
    public JwtResponse authenticateUser(@RequestBody LoginRequest request) {
    	
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        User user = userRepository.findByUsername(request.getUsername()).get();
        return new JwtResponse(jwt, user.getUsername(), user.getEmail(), user.getRole().name());
    }

    
    @PostMapping("/signup")
    public String registerUser(@RequestBody SignUpRequest request) {
    	if(userRepository.existsByUsername(request.getUsername())) {
            return "Username already exists";
        }

        if(userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }
        
        User user = new User(request.getUsername(), request.getEmail(),encoder.encode(request.getPassword()),
                request.getRole() != null && request.getRole().equals("ADMIN")? ERole.ROLE_ADMIN: ERole.ROLE_USER);
        userRepository.save(user);
        return "User registered successfully!";
    }
}
