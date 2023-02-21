// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.exception;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Exception thrown whenever an exception occurs during the use
 * of the Delegate API.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class DelegateRuntimeException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause The cause.
     */
    public DelegateRuntimeException(String message, Throwable cause) {
        super(message, cause);

        validate("message", String.class, message);
        validate("cause", Throwable.class, cause);
    }

}
