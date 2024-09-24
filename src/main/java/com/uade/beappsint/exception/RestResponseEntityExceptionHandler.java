package com.uade.beappsint.exception;

import com.uade.beappsint.dto.ErrorResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for REST controllers.
 * Provides methods to handle various exceptions and return appropriate HTTP responses.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    /**
     * Handles GenericException and returns an INTERNAL_SERVER_ERROR response.
     *
     * @param ex the exception to handle.
     * @param request the current web request.
     * @return a ResponseEntity containing the error response.
     */
    @ExceptionHandler(value
            = {GenericException.class})
    protected ResponseEntity<Object> handleGenericException(
            RuntimeException ex, WebRequest request) {
        ErrorResponseDTO bodyOfResponse = ErrorResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Handles JwtFilterException and ExpiredJwtException and returns a FORBIDDEN response.
     *
     * @param ex the exception to handle.
     * @param request the current web request.
     * @return a ResponseEntity containing the error response.
     */
    @ExceptionHandler(value
            = {JwtFilterException.class, ExpiredJwtException.class})
    protected ResponseEntity<Object> handleSignatureException(
            RuntimeException ex, WebRequest request) {
        ErrorResponseDTO bodyOfResponse = ErrorResponseDTO.builder()
                .error("Invalid token.")
                .build();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    /**
     * Handles UserAlreadyExistsException and returns a CONFLICT response.
     *
     * @param ex the exception to handle.
     * @param request the current web request.
     * @return a ResponseEntity containing the error response.
     */
    @ExceptionHandler(value
            = {UserAlreadyExistsException.class})
    protected ResponseEntity<Object> handleUserAlreadyExists(
            RuntimeException ex, WebRequest request) {
        ErrorResponseDTO bodyOfResponse = ErrorResponseDTO.builder()
                .error("User already exists.")
                .build();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    /**
     * Handles BadRequestException and returns a BAD_REQUEST response.
     *
     * @param ex the exception to handle.
     * @param request the current web request.
     * @return a ResponseEntity containing the error response.
     */
    @ExceptionHandler(value
            = {BadRequestException.class})
    protected ResponseEntity<Object> handleBadRequest(
            RuntimeException ex, WebRequest request) {
        ErrorResponseDTO bodyOfResponse = ErrorResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}