package com.healthsync.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.*;

@Service
public class JwtService {

	private final SecretKey signingkey;
	private final long expiration;
	
	public JwtService(@Value("${jwt.secret}") String secret , @Value("${jwt.expiration}") long expiration)
	{
		byte[] keyBites = Decoders.BASE64.decode(secret);
		this.signingkey = Keys.hmacShaKeyFor(keyBites);
		this.expiration = expiration;	
	}
	
	public String generateToken(String email)
	{
		
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expiration);
		
		return Jwts.builder()
				.subject(email)
				.issuedAt(now)
				.expiration(expiry)
				.signWith(signingkey)
				.compact();
		
	}
	
	public String extractEmail(String token)
	{
		return extractAllClaims(token).getSubject();	
	}

	public boolean isTokenValid(String token , String email)
	{
		Claims claims = extractAllClaims(token);
		
		return claims.getSubject().equals(email)
				&& claims.getExpiration().after(new Date());
	}
	
	private Claims extractAllClaims(String token)
	{
		return Jwts.parser()
				.verifyWith(signingkey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
}
