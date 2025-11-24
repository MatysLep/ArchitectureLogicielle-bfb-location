package imt.archi.bfb.interfaces.rest.common;

import imt.archi.bfb.interfaces.rest.common.exception.AbstractRestException;
import imt.archi.bfb.interfaces.rest.common.model.output.ExceptionOutput;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springdoc.core.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @ExceptionHandler(AbstractRestException.class)
    public ResponseEntity<ExceptionOutput> handleRestException(final HttpServletRequest request, final AbstractRestException exception) {
        return buildResponse(
                request,
                exception.getHttpStatus(),
                exception.getType(),
                exception.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionOutput> handleGenericException(final HttpServletRequest request, final Exception exception) {
        return buildResponse(
                request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionOutput> handleValidationException(
            final HttpServletRequest request,
            final MethodArgumentNotValidException exception) {

        String errors = exception.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildResponse(
                request,
                HttpStatus.BAD_REQUEST,
                "ValidationException",
                errors
        );
    }

    private ResponseEntity<ExceptionOutput> buildResponse(
            HttpServletRequest request,
            HttpStatus status,
            String type,
            String message
    ) {
        return new ResponseEntity<>(
                ExceptionOutput.builder()
                        .type(type)
                        .message(message)
                        .timestamp(LocalDateTime.now().format(FORMATTER))
                        .path(request.getRequestURI() + Optional.ofNullable(request.getQueryString()).map(query -> "?" + query).orElse(Strings.EMPTY))
                .build(),
                status
        );
    }
}
