package ru.sberbank.demo.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.sberbank.demo.app.exception.AccountNotFoundException.ERROR_CODE;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ERROR_CODE)
public class AccountNotFoundException extends ResourceNotFoundException {

    protected static final String ERROR_CODE = "ACCOUNT_NOT_FOUND_EXCEPTION";

    public AccountNotFoundException() {
    }

    public AccountNotFoundException(final String message) {
        super(message);
    }

    public AccountNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccountNotFoundException(final Throwable cause) {
        super(cause);
    }

}
