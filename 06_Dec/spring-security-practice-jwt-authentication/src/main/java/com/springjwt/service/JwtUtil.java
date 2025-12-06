package com.springjwt.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	@Value("${jwt.secret}")
    private String secretKey;
	
    @Value("${jwt.expiration}")
    private long expirationTime;
	
	public String generateJwtToken(UserDetails userDetails) {
		return Jwts.builder()
                .setSubject(userDetails.getUsername())  
                .setIssuedAt(new Date())                
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
	}
	
	public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
        		.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8))).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8))).build()
				.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
