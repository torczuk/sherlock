package com.github.torczuk.sherlock.domain.exception;

public class AppException extends RuntimeException {

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
