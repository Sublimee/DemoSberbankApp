package ru.sberbank.demo.app.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "INCORRECT_TRANSFER_TRANSACTION_EXCEPTION")
public class TransferTransactionException extends RuntimeException {

    public TransferTransactionException() {
    }

    public TransferTransactionException(final String message) {
        super(message);
    }

    public TransferTransactionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TransferTransactionException(final Throwable cause) {
        super(cause);
    }

}
