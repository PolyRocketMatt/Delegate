// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.exception;

import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;

/**
 * Exception thrown when a {@link CommandArgument} is parsed incorrectly.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class ArgumentParseException extends RuntimeException {

    private final Class<?> parseType;

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message.
     * @param parseType The type of the argument that failed to parse.
     */
    public ArgumentParseException(String message, Class<?> parseType) {
        super(message);
        this.parseType = parseType;
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause The cause.
     * @param parseType The type of the argument that failed to parse.
     */
    public ArgumentParseException(String message, Throwable cause, Class<?> parseType) {
        super(message, cause);
        this.parseType = parseType;
    }

    public Class<?> getParseType() {
        return parseType;
    }
}
