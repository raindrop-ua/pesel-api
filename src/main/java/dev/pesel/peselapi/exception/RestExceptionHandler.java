package dev.pesel.peselapi.exception;

import dev.pesel.peselapi.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.util.HtmlUtils;

import java.time.Instant;

@ControllerAdvice
public class RestExceptionHandler {

    private ResponseEntity<ErrorResponse> respond(HttpStatus status, String msg, ServletWebRequest req) {
        String safeMessage = sanitize(msg);
        String safePath = sanitize(req.getRequest().getRequestURI());
        var body = new ErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                safeMessage,
                safePath
        );
        return ResponseEntity.status(status).body(body);
    }

    private String sanitize(String value) {
        return value == null ? null : HtmlUtils.htmlEscape(value);
    }

    @ExceptionHandler({
            InvalidBirthDateException.class,
            InvalidDateRangeException.class,
            InvalidParamException.class
    })
    public ResponseEntity<ErrorResponse> handleDomain(RuntimeException ex, ServletWebRequest req) {
        String message;
        if (ex instanceof InvalidBirthDateException) {
            message = "dob must be in format dd.MM.yyyy and represent a real date";
        } else if (ex instanceof InvalidDateRangeException) {
            message = "PESEL supports only years between 1800 and 2299.";
        } else {
            message = "Invalid request parameter.";
        }
        return respond(HttpStatus.BAD_REQUEST, message, req);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, ServletWebRequest req) {
        return respond(HttpStatus.BAD_REQUEST, "Bad parameter value: " + ex.getName(), req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAny(ServletWebRequest req) {
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", req);
    }
}
