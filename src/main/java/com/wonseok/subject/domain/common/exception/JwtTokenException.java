package com.wonseok.subject.domain.common.exception;

public class JwtTokenException extends RuntimeException {
    public JwtTokenException() {
        super();
    }

    public JwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenException(String message) {
        super(message);
    }

    public JwtTokenException(Throwable cause) {
        super(cause);
    }
}
