package ru.sberbank.demo.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "CLIENT_NOT_FOUND_EXCEPTION")
public class ClientNotFoundException extends Exception {
}
