package com.wonseok.subject.domain.common.handler;

import com.wonseok.subject.domain.common.dto.ErrorResponse;
import com.wonseok.subject.domain.common.exception.ConflictException;
import com.wonseok.subject.domain.common.exception.ForbiddenException;
import com.wonseok.subject.domain.common.exception.JwtTokenException;
import com.wonseok.subject.domain.common.exception.NotFoundException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private final Map<String, Object> errors = new HashMap();

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(value = {ConflictException.class})
    @ResponseBody
    protected ErrorResponse badRequest(RuntimeException ex, WebRequest request) {
        errors.put("code", CONFLICT.value());
        errors.put("message", ex.getMessage());
        return ErrorResponse.fail(errors);
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = {ForbiddenException.class})
    @ResponseBody
    protected ErrorResponse forbidden(RuntimeException ex, WebRequest request) {
        errors.put("code", FORBIDDEN.value());
        errors.put("message", ex.getMessage());
        return ErrorResponse.fail(errors);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseBody
    protected ErrorResponse notFound(RuntimeException ex, WebRequest request) {
        errors.put("code", NOT_FOUND.value());
        errors.put("message", ex.getMessage());
        return ErrorResponse.fail(errors);
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = {JwtTokenException.class, SecurityException.class, SignatureException.class})
    @ResponseBody
    protected ErrorResponse unauthorized(RuntimeException ex, WebRequest request) {
        errors.put("code", UNAUTHORIZED.value());
        errors.put("message", ex.getMessage());
        return ErrorResponse.fail(errors);
    }

    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ExceptionHandler
    @ResponseBody
    protected ErrorResponse methodNotAllowed(RuntimeException ex, WebRequest request) {
        errors.put("code", METHOD_NOT_ALLOWED.value());
        errors.put("message", ex.getMessage());
        return ErrorResponse.fail(errors);
    }
}
