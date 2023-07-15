package com.github.polyrocketmatt.delegate.api.exception;

import org.apiguardian.api.API;

/**
 * Exception thrown when a base is used but does not exist.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public class BaseNonExistentException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     */
    public BaseNonExistentException() {
        super("Base command is not set and cannot be used");
    }

}
