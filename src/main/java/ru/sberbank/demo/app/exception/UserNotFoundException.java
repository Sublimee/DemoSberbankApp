package ru.sberbank.demo.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.sberbank.demo.app.exception.UserNotFoundException.ERROR_CODE;


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ERROR_CODE)
public class UserNotFoundException extends ResourceNotFoundException {

    protected static final String ERROR_CODE = "USER_NOT_FOUND_EXCEPTION";

    public UserNotFoundException() {
    }

    public UserNotFoundException(final String message) {
        super(message);
    }

    public UserNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(final Throwable cause) {
        super(cause);
    }

}
