package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.DefaultRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.github.polyrocketmatt.delegate.core.utils.ArrayUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a {@link CommandArgument} that parses a float from the input.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class FloatArgument extends CommandArgument<Float> {

    /**
     * Creates a new {@link FloatArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     */
    private FloatArgument(String identifier, String argumentDescription) {
        super(identifier, argumentDescription, Float.class, new NonNullRule());
    }

    /**
     * Creates a new {@link FloatArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     */
    private FloatArgument(String identifier, String argumentDescription, Float defaultValue) {
        super(identifier, argumentDescription, Float.class, new DefaultRule<>(defaultValue));
    }

    /**
     * Creates a new {@link FloatArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rule The rule of the argument.
     */
    private FloatArgument(String identifier, String argumentDescription, ArgumentRule<?> rule) {
        super(identifier, argumentDescription, Float.class, rule);
    }

    /**
     * Creates a new {@link FloatArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     */
    private FloatArgument(String identifier, String argumentDescription, Float defaultValue, ArgumentRule<?> rules) {
        super(identifier, argumentDescription, Float.class, ArrayUtils.combine(List.of(new DefaultRule<>(defaultValue)), List.of(rules)));
    }

    @Override
    public Argument<Float> parse(String input, Consumer<Exception> consumer) {
        try {
            return new Argument<>(getIdentifier(), Float.parseFloat(input));
        } catch (NumberFormatException ex) {
            consumer.accept(ex);
        }

        return getDefault();
    }

    /**
     * Creates a new {@link FloatArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link FloatArgument}.
     */
    public static FloatArgument of(String identifier, String argumentDescription) {
        return new FloatArgument(identifier, argumentDescription);
    }

    /**
     * Creates a new {@link FloatArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The created {@link FloatArgument}.
     */
    public static FloatArgument of(String identifier, String argumentDescription, float defaultValue) {
        return new FloatArgument(identifier, argumentDescription, defaultValue);
    }

    /**
     * Creates a new {@link FloatArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rule The rule of the argument.
     * @return The created {@link FloatArgument}.
     */
    public static FloatArgument of(String identifier, String argumentDescription, ArgumentRule<?> rule) {
        return new FloatArgument(identifier, argumentDescription, rule);
    }

    /**
     * Creates a new {@link FloatArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link FloatArgument}.
     */
    public static FloatArgument of(String identifier, String argumentDescription, float defaultValue, ArgumentRule<?> rules) {
        return new FloatArgument(identifier, argumentDescription, defaultValue, rules);
    }

}
