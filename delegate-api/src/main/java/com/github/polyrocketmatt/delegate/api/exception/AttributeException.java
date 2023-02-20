// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.exception;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Exception thrown when some illegal state concerning an {@link com.github.polyrocketmatt.delegate.api.command.CommandAttribute}
 * is encountered.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class AttributeException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public AttributeException(String message) {
        super(message);

        validate(message);
    }

}
