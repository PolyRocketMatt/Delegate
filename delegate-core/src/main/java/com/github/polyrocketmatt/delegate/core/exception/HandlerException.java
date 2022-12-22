package com.github.polyrocketmatt.delegate.core.exception;

/**
 * @author Matthias Kovacic
 * @since 0.0.1
 *
 * Exception that is thrown when a handler fails at any point.
 */
public class HandlerException extends RuntimeException {

    /**
     * Create a new HandlerException.
     */
    public HandlerException() {
        super();
    }

    /**
     * Create a new HandlerException with the given message.
     *
     * @param message The message to use.
     */
    public HandlerException(String message) {
        super(message);
    }

    /**
     * Create a new HandlerException with the given message and cause.
     *
     * @param message The message to use.
     * @param cause The cause to use.
     */
    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new HandlerException with the given cause.
     *
     * @param cause The cause to use.
     */
    public HandlerException(Throwable cause) {
        super(cause);
    }

    protected HandlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}