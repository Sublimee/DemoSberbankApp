package ru.sberbank.demo.app.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.exception.transaction.DepositTransactionException;
import ru.sberbank.demo.app.exception.transaction.TransferTransactionException;
import ru.sberbank.demo.app.exception.transaction.WithdrawTransactionException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class ErrorAdvice {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return error(INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler({AccountNotFoundException.class})
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException e) {
        return error(NOT_FOUND, e);
    }

    @ExceptionHandler({ClientNotFoundException.class})
    public ResponseEntity<String> handleClientNotFoundExceptionException(ClientNotFoundException e) {
        return error(NOT_FOUND, e);
    }

    @ExceptionHandler({DepositTransactionException.class})
    public ResponseEntity<String> handleDepositTransactionException(DepositTransactionException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({TransferTransactionException.class})
    public ResponseEntity<String> handleTransferTransactionException(TransferTransactionException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({WithdrawTransactionException.class})
    public ResponseEntity<String> handleWithdrawTransactionException(WithdrawTransactionException e) {
        return error(BAD_REQUEST, e);
    }

    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        log.error("Exception : ", e);
        return ResponseEntity.status(status).body(e.toString());
    }

}