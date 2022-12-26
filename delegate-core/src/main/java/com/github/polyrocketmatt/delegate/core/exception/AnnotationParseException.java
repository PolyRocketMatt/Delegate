package com.github.polyrocketmatt.delegate.core.exception;

public class AnnotationParseException extends RuntimeException {

    public AnnotationParseException(String message) {
        super(message);
    }

    public AnnotationParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnotationParseException(Throwable cause) {
        super(cause);
    }

    public AnnotationParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
