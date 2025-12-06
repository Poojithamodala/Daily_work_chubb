package com.springjwt.response;

import lombok.Data;

@Data
public class JwtResponse {
	private String token;
    private String username;
    private String email;
    private String role;
    
    public JwtResponse(String token, String username, String email, String role) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
