package com.github.polyrocketmatt.delegate.core.exception;

public class ArgumentParseException extends RuntimeException {

    public ArgumentParseException(String message) {
        super(message);
    }

    public ArgumentParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentParseException(Throwable cause) {
        super(cause);
    }

    public ArgumentParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
