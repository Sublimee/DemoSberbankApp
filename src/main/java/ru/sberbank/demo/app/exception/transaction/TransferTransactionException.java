package ru.sberbank.demo.app.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "INCORRECT_TRANSFER_TRANSACTION_EXCEPTION")
public class TransferTransactionException extends AbstractTransaction {

    public TransferTransactionException(String message) {
        super(message);
    }

}
