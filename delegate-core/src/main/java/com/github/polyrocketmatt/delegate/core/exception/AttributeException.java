package com.github.polyrocketmatt.delegate.core.exception;

/**
 * @author Matthias Kovacic
 * @since 0.0.1
 *
 * Exception that is thrown when an illegal state concerning
 * a {@link com.github.polyrocketmatt.delegate.core.command.CommandAttribute} is encountered.
 */
public class AttributeException extends RuntimeException {

    /**
     * Create a new AttributeException.
     */
    public AttributeException() {
        super();
    }

    /**
     * Create a new AttributeException with the given message.
     *
     * @param message The message to use.
     */
    public AttributeException(String message) {
        super(message);
    }

    /**
     * Create a new AttributeException with the given message and cause.
     *
     * @param message The message to use.
     * @param cause The cause to use.
     */
    public AttributeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new AttributeException with the given cause.
     *
     * @param cause The cause to use.
     */
    public AttributeException(Throwable cause) {
        super(cause);
    }

    protected AttributeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
