package ru.sberbank.demo.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "ACCOUNT_NOT_FOUND_EXCEPTION")
public class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String message) {
        super(message);
    }

}
