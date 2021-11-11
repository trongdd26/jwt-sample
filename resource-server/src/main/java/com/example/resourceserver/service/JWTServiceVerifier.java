package com.example.resourceserver.service;

import io.jsonwebtoken.Claims;

public interface JWTServiceVerifier {
    Claims getAllClaimsFromToken(String token);
}
