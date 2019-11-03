package ru.sberbank.demo.app.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "INCORRECT_WITHDRAW_TRANSACTION_EXCEPTION")
public class WithdrawTransactionException extends AbstractTransaction {

    public WithdrawTransactionException(String message) {
        super(message);
    }

}
