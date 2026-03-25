package com.movies.watchlist.exception;

// @RestControllerAdvice = watches ALL controllers in the project
// Whenever any controller throws an exception, this class intercepts it
// Instead of Spring returning a raw 500 crash with a stack trace,
// this class returns a clean JSON error message
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler(RuntimeException.class) means:
    // "catch any RuntimeException thrown anywhere in the app"
    // All our .orElseThrow(() -> new RuntimeException("...")) calls land here
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        // ResponseEntity lets you control:
        //   - the HTTP status code (404, 400, 500 etc.)
        //   - the response body (what the client sees)
        // ex.getMessage() returns whatever string you passed to RuntimeException
        // e.g. "Movie not found with id: 5"
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)  // sends HTTP 404
                .body(ex.getMessage());        // sends the error message as plain text
    }

    // Catches any other unexpected exception that isn't a RuntimeException
    // Acts as a safety net — client always gets a readable message, never a raw crash
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)  // sends HTTP 500
                .body("Something went wrong: " + ex.getMessage());
    }
}