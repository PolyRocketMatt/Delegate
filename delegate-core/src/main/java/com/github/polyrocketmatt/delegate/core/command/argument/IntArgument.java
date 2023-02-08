// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a {@link CommandArgument} that parses an integer from the input.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class IntArgument extends CommandArgument<Integer> {

    /**
     * Creates a new {@link IntArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param isOptional Whether the argument is optional.
     * @param rules The rules of the argument.
     */
    public IntArgument(String identifier, String argumentDescription, Integer defaultValue, boolean isOptional, List<ArgumentRule<?>> rules) {
        super(identifier, argumentDescription, Integer.class, new Argument<>(identifier, defaultValue), isOptional, rules);
    }

    @Override
    public Argument<Integer> parse(String input, Consumer<Exception> onFail) {
        try {
            return new Argument<>(getIdentifier(), Integer.parseInt(input));
        } catch (NumberFormatException ex) {
            onFail.accept(ex);
        }

        return getDefault();
    }

    @Override
    public Integer parse(StringReader reader) throws CommandSyntaxException {
        int start = reader.getCursor();
        return reader.readInt();
    }

    /**
     * Creates a new {@link IntArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link IntArgument}.
     */
    public static IntArgument of(String identifier, String argumentDescription) {
        return new IntArgument(identifier, argumentDescription, null, false, List.of());
    }

    /**
     * Creates a new {@link IntArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The created {@link IntArgument}.
     */
    public static IntArgument of(String identifier, String argumentDescription, int defaultValue) {
        return new IntArgument(identifier, argumentDescription, defaultValue, false, List.of());
    }

    /**
     * Creates a new {@link IntArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link IntArgument}.
     */
    public static IntArgument of(String identifier, String argumentDescription, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new IntArgument(identifier, argumentDescription, null, isOptional, List.of(rules));
    }

    /**
     * Creates a new {@link IntArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link IntArgument}.
     */
    public static IntArgument of(String identifier, String argumentDescription, int defaultValue, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new IntArgument(identifier, argumentDescription, defaultValue, isOptional, List.of(rules));
    }
}
