package ru.sberbank.demo.app.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.sberbank.demo.app.controller.handler.ErrorCode;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = DepositTransactionException.ERROR_CODE)
public class DepositTransactionException extends RuntimeException implements ErrorCode {

    protected static final String ERROR_CODE = "INCORRECT_DEPOSIT_TRANSACTION_EXCEPTION";

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

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }

}
