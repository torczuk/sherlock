package com.github.torczuk.sherlock.domain.exception;

import java.io.IOException;

public class AppException extends RuntimeException {

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable e) {
        super(e);
    }
}
