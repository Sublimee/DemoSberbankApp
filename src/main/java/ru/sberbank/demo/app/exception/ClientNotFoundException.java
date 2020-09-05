package ru.sberbank.demo.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.sberbank.demo.app.exception.ClientNotFoundException.ERROR_CODE;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ERROR_CODE)
public class ClientNotFoundException extends ResourceNotFoundException {

    protected static final String ERROR_CODE = "CLIENT_NOT_FOUND_EXCEPTION";

    public ClientNotFoundException() {
    }

    public ClientNotFoundException(final String message) {
        super(message);
    }

    public ClientNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ClientNotFoundException(final Throwable cause) {
        super(cause);
    }

}
