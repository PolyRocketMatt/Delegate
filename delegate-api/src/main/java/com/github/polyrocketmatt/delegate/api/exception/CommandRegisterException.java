// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.exception;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Exception thrown when the register of a {@link com.github.polyrocketmatt.delegate.api.command.IDelegateCommand} fails.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class CommandRegisterException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public CommandRegisterException(String message) {
        super(message);

        validate("message", String.class, message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause The cause.
     */
    public CommandRegisterException(String message, Throwable cause) {
        super(message, cause);

        validate("message", String.class, message);
        validate("cause", Throwable.class, cause);
    }

}
