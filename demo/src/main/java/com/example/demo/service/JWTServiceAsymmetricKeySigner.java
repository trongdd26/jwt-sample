package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Profile("jwt-asymmetricKey")
public class JWTServiceAsymmetricKeySigner implements JWTServiceSigner{
    private static Logger LOGGER = Logger.getLogger("JWTServiceAsymmetricKeySigner");

    @Value("${jwt.keyPath}")
    private String keyPath;

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public String generateToken(String username) {
        try(InputStream resourceAsStream = resourceLoader.getResource(this.keyPath).getInputStream()) {
            int totalBytes = resourceAsStream.available();
            byte[] keyInByteArray = new byte[totalBytes];
            resourceAsStream.read(keyInByteArray);

            Map<String, Object> claims = new HashMap<>();
            PKCS8EncodedKeySpec spec =
                    new PKCS8EncodedKeySpec(keyInByteArray);

            KeyFactory kf = KeyFactory.getInstance("RSA");
            return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                    .signWith(SignatureAlgorithm.RS512, kf.generatePrivate(spec)).compact();
        }
        catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ioe) {
            ioe.printStackTrace();
            LOGGER.log(Level.SEVERE, ioe.getLocalizedMessage());
            throw new RuntimeException();
        }
    }
}
