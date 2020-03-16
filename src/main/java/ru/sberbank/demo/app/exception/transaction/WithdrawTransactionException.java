package ru.sberbank.demo.app.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "INCORRECT_WITHDRAW_TRANSACTION_EXCEPTION")
public class WithdrawTransactionException extends RuntimeException {

    public WithdrawTransactionException() {
    }

    public WithdrawTransactionException(final String message) {
        super(message);
    }

    public WithdrawTransactionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public WithdrawTransactionException(final Throwable cause) {
        super(cause);
    }

}
