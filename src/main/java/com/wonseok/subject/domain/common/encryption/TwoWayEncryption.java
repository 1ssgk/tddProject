package com.wonseok.subject.domain.common.encryption;

import java.security.NoSuchAlgorithmException;

public interface TwoWayEncryption {
    public String encrypt(String str) throws NoSuchAlgorithmException;

    public String decrypt(String str) throws NoSuchAlgorithmException;
}
