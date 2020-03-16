package ru.sberbank.demo.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "CLIENT_NOT_FOUND_EXCEPTION")
public class ClientNotFoundException extends RuntimeException {

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
