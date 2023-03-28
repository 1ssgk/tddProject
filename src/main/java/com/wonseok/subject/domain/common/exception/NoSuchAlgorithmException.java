package com.wonseok.subject.domain.common.exception;

public class NoSuchAlgorithmException extends RuntimeException {
    public NoSuchAlgorithmException() {
        super();
    }

    public NoSuchAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchAlgorithmException(String message) {
        super(message);
    }

    public NoSuchAlgorithmException(Throwable cause) {
        super(cause);
    }
}
