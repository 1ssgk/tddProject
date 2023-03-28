package com.wonseok.subject.domain.common.encryption;

import java.security.NoSuchAlgorithmException;

public interface OneWayEncryption {
    public String encrypt(String str) throws NoSuchAlgorithmException;
}
