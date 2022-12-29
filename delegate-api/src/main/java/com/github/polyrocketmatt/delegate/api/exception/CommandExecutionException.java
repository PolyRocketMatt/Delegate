package com.github.polyrocketmatt.delegate.api.exception;

/**
 * Exception thrown when the execution of a {@link com.github.polyrocketmatt.delegate.core.command.DelegateCommand} fails.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class CommandExecutionException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public CommandExecutionException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause The cause.
     */
    public CommandExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param cause The cause.
     */
    public CommandExecutionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message, cause, suppression enabled or disabled,
     *
     * @param message The detail message.
     * @param cause The cause.
     * @param enableSuppression Whether suppression is enabled or disabled.
     * @param writableStackTrace Whether the stack trace should be writable.
     */
    public CommandExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
