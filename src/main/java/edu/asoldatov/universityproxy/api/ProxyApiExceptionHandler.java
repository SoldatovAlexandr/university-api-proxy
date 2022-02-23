package edu.asoldatov.universityproxy.api;

import edu.asoldatov.universityproxy.dto.client.ApiError;
import edu.asoldatov.universityproxy.dto.client.Error;
import edu.asoldatov.universityproxy.exception.AuthException;
import edu.asoldatov.universityproxy.exception.IntegrationException;
import edu.asoldatov.universityproxy.exception.NotFoundException;
import edu.asoldatov.universityproxy.exception.TransformException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class ProxyApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_MESSAGE = "Уважаемый пользователь! Произошла непредвиденная ошибка.";

    private final MessageSource messageSource;

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleAll(final Exception ex) {
        return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "exception.internal.server", ex);
    }

    @ExceptionHandler({IntegrationException.class})
    public ResponseEntity<ApiError> handleIntegrationException(final IntegrationException ex) {
        return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "exception.omstu.integration", ex);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiError> handleNotFoundException(final NotFoundException ex) {
        return getResponseEntity(HttpStatus.NOT_FOUND, "exception.not.found", ex);
    }

    @ExceptionHandler({TransformException.class})
    public ResponseEntity<ApiError> handleTransformException(final TransformException ex) {
        return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "exception.transform.result", ex);
    }

    @ExceptionHandler({AuthException.class})
    public ResponseEntity<ApiError> handleAuthException(final AuthException ex) {
        return getResponseEntity(HttpStatus.BAD_REQUEST, "exception.omstu.auth", ex);
    }

    private ResponseEntity<ApiError> getResponseEntity(final HttpStatus httpStatus,
                                                       final String codeMessage,
                                                       final Throwable ex) {
        Throwable exception = ex;
        while (exception != null && StringUtils.isNotBlank(exception.getLocalizedMessage())) {
            exception = exception.getCause();
        }
        log.error("Exception occurred: " + ex.getMessage(), ex);

        ApiError apiError = new ApiError(httpStatus,
                Collections.singletonList(
                        Error.builder()
                                .errorCode(httpStatus.value())
                                .message(getMessage(codeMessage, DEFAULT_MESSAGE))
                                .detail(ex.getLocalizedMessage())
                                .build()
                ));

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    private String getMessage(final String codeMessage, final String defaultMessage) {
        return Optional.ofNullable(messageSource.getMessage(
                codeMessage,
                null,
                null,
                Locale.getDefault())).orElse(defaultMessage);
    }
}
