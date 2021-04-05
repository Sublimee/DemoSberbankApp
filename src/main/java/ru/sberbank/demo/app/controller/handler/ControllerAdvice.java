package ru.sberbank.demo.app.controller.handler;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sberbank.demo.app.exception.EmailExistsException;
import ru.sberbank.demo.app.exception.transaction.TransferTransactionException;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ConditionalOnProperty(name = "app.errors.controlleradvice", havingValue = "true")
@RestControllerAdvice
public class ControllerAdvice {

    @Value("${app.sendreport.uri}")
    private String sendReportUri;
    @Value("${app.api.version}")
    private String currentApiVersion;

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ExceptionResponse> handleEmailExistsException(HttpServletRequest request,
                                                                        EmailExistsException ex) {
        final ExceptionResponse error = new ExceptionResponse(
                currentApiVersion,
                ex.getErrorCode(),
                "Измените параметры запроса и повторите попытку",
                request.getRequestURI(),
                ex.getErrorCode(),
                ex.getMessage(),
                sendReportUri
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransferTransactionException.class)
    public ResponseEntity<ExceptionResponse> handleTransferTransactionException(HttpServletRequest request,
                                                                                TransferTransactionException ex) {
        final ExceptionResponse error = new ExceptionResponse(
                currentApiVersion,
                ex.getErrorCode(),
                "Измените параметры запроса и повторите попытку",
                request.getRequestURI(),
                ex.getErrorCode(),
                ex.getMessage(),
                sendReportUri
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<ExceptionResponse> handleRollbackException(HttpServletRequest request, RollbackException ex) {
        String reason;
        String message = ex.getMessage();
        try {
            reason = ((ConstraintViolationException) ex.getCause()).getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            reason = ex.getCause().getMessage();
        }

        final ExceptionResponse error = new ExceptionResponse(
                currentApiVersion,
                HttpStatus.BAD_REQUEST.toString(),
                "Измените параметры запроса и повторите попытку",
                request.getRequestURI(),
                reason,
                message,
                sendReportUri
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
