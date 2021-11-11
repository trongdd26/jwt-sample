package com.example.resourceserver.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("jwt-secretKey")
public class JWTServiceSecretKeyVerifier implements JWTServiceVerifier{
    @Value("${jwt.secretkey}")
    private String jwtSecretKey;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(this.jwtSecretKey).parseClaimsJws(token).getBody();
    }
}
