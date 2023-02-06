package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.DefaultRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.github.polyrocketmatt.delegate.core.utils.ArrayUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a {@link CommandArgument} that parses a string from the input.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class StringArgument extends CommandArgument<String> {

    /**
     * Creates a new {@link StringArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     */
    public StringArgument(String identifier, String argumentDescription) {
        super(identifier, argumentDescription, String.class, new NonNullRule());
    }

    /**
     * Creates a new {@link StringArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     */
    public StringArgument(String identifier, String argumentDescription, String defaultValue) {
        super(identifier, argumentDescription, String.class, new DefaultRule<>(defaultValue));
    }

    /**
     * Creates a new {@link StringArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rule The rule of the argument.
     */
    public StringArgument(String identifier, String argumentDescription, ArgumentRule<?> rule) {
        super(identifier, argumentDescription, String.class, rule);
    }

    /**
     * Creates a new {@link StringArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     */
    public StringArgument(String identifier, String argumentDescription, String defaultValue, ArgumentRule<?> rules) {
        super(identifier, argumentDescription, String.class, ArrayUtils.combine(List.of(new DefaultRule<>(defaultValue)), List.of(rules)));
    }

    @Override
    public Argument<String> parse(String input, Consumer<Exception> onFail) {
        return (input == null) ? getDefault() : new Argument<>(getIdentifier(), input);
    }

    /**
     * Creates a new {@link StringArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link StringArgument}.
     */
    public static StringArgument of(String identifier, String argumentDescription) {
        return new StringArgument(identifier, argumentDescription);
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
        return new StringArgument(identifier, argumentDescription, defaultValue);
    }

    /**
     * Creates a new {@link StringArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rule The rule of the argument.
     * @return The created {@link StringArgument}.
     */
    public static StringArgument of(String identifier, String argumentDescription, ArgumentRule<?> rule) {
        return new StringArgument(identifier, argumentDescription, rule);
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
    public static StringArgument of(String identifier, String argumentDescription, String defaultValue, ArgumentRule<?> rules) {
        return new StringArgument(identifier, argumentDescription, defaultValue, rules);
    }

}
