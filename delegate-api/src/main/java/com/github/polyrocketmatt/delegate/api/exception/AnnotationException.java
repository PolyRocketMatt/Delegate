package com.github.polyrocketmatt.delegate.api.exception;

import org.apiguardian.api.API;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Exception thrown when some illegal state concerning a Delegate annotation
 * is encountered.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public class AnnotationException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public AnnotationException(String message) {
        super(message);

        validate("message", String.class, message);
    }

}