package ru.sberbank.demo.app.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "INCORRECT_DEPOSIT_TRANSACTION_EXCEPTION")
public class DepositTransactionException extends AbstractTransaction {

    public DepositTransactionException(String message) {
        super(message);
    }

}
