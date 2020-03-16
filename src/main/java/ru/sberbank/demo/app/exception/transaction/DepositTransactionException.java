package ru.sberbank.demo.app.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "INCORRECT_DEPOSIT_TRANSACTION_EXCEPTION")
public class DepositTransactionException extends RuntimeException {

    public DepositTransactionException() {
    }

    public DepositTransactionException(final String message) {
        super(message);
    }

    public DepositTransactionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DepositTransactionException(final Throwable cause) {
        super(cause);
    }

}
