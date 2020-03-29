package ru.sberbank.demo.app.controller.handlers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sberbank.demo.app.exception.transaction.TransferTransactionException;

import javax.servlet.http.HttpServletRequest;

@ConditionalOnProperty(name = "app.errors.controlleradvice", havingValue = "true")
@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @Value("${app.sendreport.uri}")
    private String sendReportUri;
    @Value("${app.api.version}")
    private String currentApiVersion;

    @ExceptionHandler(TransferTransactionException.class)
    public ResponseEntity<ErrorResponse> handleNonExistingHero(HttpServletRequest request,
                                                               TransferTransactionException ex) {
        final ErrorResponse error = new ErrorResponse(
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

}
