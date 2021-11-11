package com.example.demo.service;

public interface JWTServiceSigner {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    String generateToken(String username);
}
