// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a {@link CommandArgument} that parses a string from the input.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class StringArgument extends CommandArgument<String> {

    /**
     * Creates a new {@link StringArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param isOptional Whether the argument is optional.
     * @param rules The rules of the argument.
     */
    public StringArgument(String identifier, String argumentDescription, String defaultValue, boolean isOptional, List<ArgumentRule<?>> rules) {
        super(identifier, argumentDescription, String.class, new Argument<>(identifier, defaultValue), isOptional, rules);
    }

    @Override
    public @NotNull Argument<String> parse(@NotNull String input) {
        if (input != null) {
            //  Check if string starts and ends with a double quote
            if (input.startsWith("\"") && input.endsWith("\"")) {
                //  Remove the double quotes and return all the characters in between
                return new Argument<>(getIdentifier(), input.substring(1, input.length() - 1));
            } else
                //  Otherwise, we return the first part only
                return new Argument<>(getIdentifier(), input.split(" ")[0]);
        } else
            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a string", String.class);
            return getDefault();
    }

    @Override
    public @NotNull String parse(@NotNull StringReader reader) throws ArgumentParseException {
        try {
            //  Check if the string in the reader starts and ends with a double quote
            if (reader.canRead() && reader.peek() == '"') {
                //  Skip the double quote
                reader.skip();

                //  Read the string until the next double quote
                String string = reader.readStringUntil('"');

                //  Skip the double quote
                reader.skip();

                //  Return the string
                return string;
            } else
                //  Otherwise, we return the first part only
                return reader.readString();
        } catch (Exception ex) {
            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a string", String.class);
            else
                return getDefault().output();
        }
    }

    /**
     * Creates a new {@link StringArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link StringArgument}.
     */
    public static StringArgument of(String identifier, String argumentDescription) {
        return new StringArgument(identifier, argumentDescription, null, false, List.of());
    }

    /**
     * Creates a new {@link StringArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The created {@link StringArgument}.
     */
    public static StringArgument of(String identifier, String argumentDescription, String defaultValue) {
        return new StringArgument(identifier, argumentDescription, defaultValue, false, List.of());
    }

    /**
     * Creates a new {@link StringArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link StringArgument}.
     */
    public static StringArgument of(String identifier, String argumentDescription, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new StringArgument(identifier, argumentDescription, null, isOptional, List.of(rules));
    }

    /**
     * Creates a new {@link StringArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link StringArgument}.
     */
    public static StringArgument of(String identifier, String argumentDescription, String defaultValue, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new StringArgument(identifier, argumentDescription, defaultValue, isOptional, List.of(rules));
    }

}
