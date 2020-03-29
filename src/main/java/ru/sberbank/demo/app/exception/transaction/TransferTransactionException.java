package ru.sberbank.demo.app.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.sberbank.demo.app.controller.handler.ErrorCode;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = TransferTransactionException.ERROR_CODE)
public class TransferTransactionException extends RuntimeException implements ErrorCode {

    protected static final String ERROR_CODE = "INCORRECT_TRANSFER_TRANSACTION_EXCEPTION";

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

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }

}
