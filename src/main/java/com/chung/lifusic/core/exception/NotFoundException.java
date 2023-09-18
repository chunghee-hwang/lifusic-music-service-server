package com.chung.lifusic.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
