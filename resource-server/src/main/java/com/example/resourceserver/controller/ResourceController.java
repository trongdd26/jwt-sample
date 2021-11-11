package com.example.resourceserver.controller;

import com.example.resourceserver.service.JWTServiceVerifier;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class ResourceController {
    @Autowired
    private JWTServiceVerifier jwtServiceVerifier;

    private static Logger LOGGER = Logger.getLogger("ResourceController");

    @GetMapping("/info")
    public ResponseEntity<?> getTokenInfo(@RequestHeader( "Authentication") String token) {
        LOGGER.log(Level.INFO, "TOKEN: " + token);
        Claims claims = jwtServiceVerifier.getAllClaimsFromToken(token);
        return ResponseEntity.ok(claims);
    }
}
