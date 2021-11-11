package com.example.resourceserver.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Profile("jwt-asymmetricKey")
public class JWTServiceAsymmetricKeyVerifier implements JWTServiceVerifier{

    private static Logger LOGGER = Logger.getLogger("JWTServiceAsymmetricKeyVerifier");

    @Value("${jwt.keyPath}")
    private String keyPath;

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public Claims getAllClaimsFromToken(String token) {
        try(InputStream resourceAsStream = resourceLoader.getResource(this.keyPath).getInputStream()) {
            int totalBytes = resourceAsStream.available();
            byte[] keyInByteArray = new byte[totalBytes];
            resourceAsStream.read(keyInByteArray);

            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(keyInByteArray));
            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        }
        catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ioe) {
            ioe.printStackTrace();
            LOGGER.log(Level.SEVERE, ioe.getLocalizedMessage());
        }
        return null;
    }
}
