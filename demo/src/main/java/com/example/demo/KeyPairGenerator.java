package com.example.demo;

import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public class KeyPairGenerator {
    public static void main(String args[]) throws IOException, NoSuchAlgorithmException {

        java.security.KeyPairGenerator keyGen = java.security.KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.genKeyPair();
        FileCopyUtils.copy(keyPair.getPublic().getEncoded(), new File("C:/publicKey"));
        FileCopyUtils.copy(keyPair.getPrivate().getEncoded(), new File("C:/privateKey"));
    }
}
