package com.wonseok.subject.domain.common.encryption;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class Sha512 implements OneWayEncryption {
    @Override
    public String encrypt(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.reset();
        md.update(str.getBytes(StandardCharsets.UTF_8));
        str = String.format("%128x", new BigInteger(1, md.digest()));
        return str;
    }
}
