package dev.pesel.peselapi.exception;

import dev.pesel.peselapi.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@ControllerAdvice
public class RestExceptionHandler {

    private ResponseEntity<ErrorResponse> respond(HttpStatus status, String msg, ServletWebRequest req) {
        var body = new ErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                msg,
                req.getRequest().getRequestURI()
        );
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler({
            InvalidBirthDateException.class,
            InvalidDateRangeException.class,
            InvalidParamException.class
    })
    public ResponseEntity<ErrorResponse> handleDomain(RuntimeException ex, ServletWebRequest req) {
        return respond(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, ServletWebRequest req) {
        return respond(HttpStatus.BAD_REQUEST, "Bad parameter value: " + ex.getName(), req);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArg(IllegalArgumentException ex, ServletWebRequest req) {
        return respond(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAny(ServletWebRequest req) {
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", req);
    }
}