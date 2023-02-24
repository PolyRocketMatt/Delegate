// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.argument;

import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;
import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

/**
 * Defines a parser that has the ability to parse a string into a specific type.
 *
 * @param <T> The type that this parser will parse into.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public interface ArgumentParser<T> {

    /**
     * Parses the given string into the type that this parser is defined to parse into.
     *
     * @param input The string to parse.
     * @return The parsed argument.
     */
    default @NotNull Argument<T> parse(@NotNull String input) {
        validate("input", String.class, input);

        return new Argument<>(newId(), null);
    }

    /**
     * Wraps an exception that was thrown when a parser fails to parse a string into a specific type.
     *
     * @param input The string that failed to parse.
     * @param wrappedException The exception that was thrown when parsing the string.
     * @param parseType The type that the string was supposed to be parsed into.
     * @return The wrapped exception.
     */
    default ArgumentParseException onFail(@NotNull String input, @Nullable Exception wrappedException, @NotNull Class<T> parseType) {
        validate("input", String.class, input);
        validate("parseType", Class.class, parseType);

        return (wrappedException == null) ?
                new ArgumentParseException("Failed to parse argument \"%s\"".formatted(input), parseType)
            :
                new ArgumentParseException("Failed to parse argument \"%s\"".formatted(input), wrappedException, parseType);
    }

}
