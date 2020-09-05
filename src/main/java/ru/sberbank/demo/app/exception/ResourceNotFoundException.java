package ru.sberbank.demo.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.sberbank.demo.app.controller.handler.ErrorCode;

import static ru.sberbank.demo.app.exception.ResourceNotFoundException.ERROR_CODE;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ERROR_CODE)
public class ResourceNotFoundException extends Exception implements ErrorCode {

    protected static final String ERROR_CODE = "RESOURCE_NOT_FOUND_EXCEPTION";

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }

}
