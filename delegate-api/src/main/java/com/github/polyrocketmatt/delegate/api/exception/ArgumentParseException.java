// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.exception;

import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import org.jetbrains.annotations.NotNull;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Exception thrown when a {@link CommandArgument} is parsed incorrectly.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class ArgumentParseException extends RuntimeException {

    private final @NotNull Class<?> parseType;

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message.
     * @param parseType The type of the argument that failed to parse.
     */
    public ArgumentParseException(@NotNull String message, @NotNull Class<?> parseType) {
        super(message);
        validate(message, parseType);

        this.parseType = parseType;
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause The cause.
     * @param parseType The type of the argument that failed to parse.
     */
    public ArgumentParseException(@NotNull String message, @NotNull Throwable cause, @NotNull Class<?> parseType) {
        super(message, cause);
        validate(message, cause, parseType);

        this.parseType = parseType;
    }

    public @NotNull Class<?> getParseType() {
        return parseType;
    }
}
