package com.wonseok.subject.domain.common.exception;

import java.io.IOException;

public class SignatureException extends IOException {

    public SignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignatureException(String message) {
        super(message);
    }

    public SignatureException(Throwable cause) {
        super(String.valueOf(cause));
    }
}
