package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.JWTServiceSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class JwtAuthenticationController {

    @Autowired
    private JWTServiceSigner serviceSigner;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user){
        if (this.authentication(user)) {
            String jwtToken = serviceSigner.generateToken(user.getUsername());
            return ResponseEntity.ok(jwtToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Validate user credentials
     * @param user
     * @return
     */
    private boolean authentication(User user) {
        return "trong".equals(user.getUsername());
    }
}
