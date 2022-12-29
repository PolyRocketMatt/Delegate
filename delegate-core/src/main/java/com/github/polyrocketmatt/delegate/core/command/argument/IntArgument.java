package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.DefaultRule;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.github.polyrocketmatt.delegate.core.utils.ArrayUtils;

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
     * Creates a new {@link IntArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     */
    public IntArgument(String identifier, String argumentDescription) {
        super(identifier, argumentDescription, new NonNullRule());
    }

    /**
     * Creates a new {@link IntArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     */
    public IntArgument(String identifier, String argumentDescription, Integer defaultValue) {
        super(identifier, argumentDescription, new DefaultRule<>(defaultValue));
    }

    /**
     * Creates a new {@link IntArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rule The rule of the argument.
     */
    public IntArgument(String identifier, String argumentDescription, ArgumentRule<String, ?> rule) {
        super(identifier, argumentDescription, rule);
    }

    /**
     * Creates a new {@link IntArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     */
    public IntArgument(String identifier, String argumentDescription, Integer defaultValue, ArgumentRule<String, ?> rules) {
        super(identifier, argumentDescription, ArrayUtils.combine(List.of(new DefaultRule<>(defaultValue)), List.of(rules)));
    }

    @Override
    public Argument<Integer> parse(String input, Consumer<Exception> onFail) {
        try {
            return new Argument<>(Integer.parseInt(input));
        } catch (NumberFormatException ex) {
            onFail.accept(ex);
        }

        return getDefault();
    }

    /**
     * Creates a new {@link IntArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link IntArgument}.
     */
    public static IntArgument of(String identifier, String argumentDescription) {
        return new IntArgument(identifier, argumentDescription);
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
        return new IntArgument(identifier, argumentDescription, defaultValue);
    }

    /**
     * Creates a new {@link IntArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rule The rule of the argument.
     * @return The created {@link IntArgument}.
     */
    public static IntArgument of(String identifier, String argumentDescription, ArgumentRule<String, ?> rule) {
        return new IntArgument(identifier, argumentDescription, rule);
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
    public static IntArgument of(String identifier, String argumentDescription, int defaultValue, ArgumentRule<String, ?> rules) {
        return new IntArgument(identifier, argumentDescription, defaultValue, rules);
    }
}
