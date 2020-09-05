package ru.sberbank.demo.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.sberbank.demo.app.controller.handler.ErrorCode;

import static ru.sberbank.demo.app.exception.EmailExistsException.ERROR_CODE;


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ERROR_CODE)
public class EmailExistsException extends Exception implements ErrorCode {

    protected static final String ERROR_CODE = "EMAIL_ALREADY_EXISTS_EXCEPTION";

    public EmailExistsException() {
    }

    public EmailExistsException(String message) {
        super(message);
    }

    public EmailExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailExistsException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }

}
