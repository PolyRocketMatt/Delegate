package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a {@link CommandArgument} that parses a double from the input.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class DoubleArgument extends CommandArgument<Double> {

    /**
     * Creates a new {@link DoubleArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param isOptional Whether the argument is optional.
     * @param rules The rules of the argument.
     */
    private DoubleArgument(String identifier, String argumentDescription, Double defaultValue, boolean isOptional, List<ArgumentRule<?>> rules) {
        super(identifier, argumentDescription, Double.class, new Argument<>(identifier, defaultValue), isOptional, rules);
    }

    @Override
    public @NotNull Argument<Double> parse(@Nullable String input) {
        if (input == null) {
            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a double", Double.class);
            return getDefault();
        }

        try {
            return new Argument<>(getIdentifier(), Double.parseDouble(input));
        } catch (NumberFormatException ex) {
            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a double", Double.class);
            return getDefault();
        }
    }

    @Override
    public @NotNull Double parse(@NotNull StringReader reader) {
        int start = reader.getCursor();
        try {
            return reader.readDouble();
        } catch (CommandSyntaxException ex) {
            reader.setCursor(start);

            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a float", Float.class);
            return getDefault().output();
        }
    }

    /**
     * Creates a new {@link DoubleArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link DoubleArgument}.
     */
    public static DoubleArgument of(String identifier, String argumentDescription) {
        return new DoubleArgument(identifier, argumentDescription, null, false, List.of());
    }

    /**
     * Creates a new {@link DoubleArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The created {@link DoubleArgument}.
     */
    public static DoubleArgument of(String identifier, String argumentDescription, double defaultValue) {
        return new DoubleArgument(identifier, argumentDescription, defaultValue, false, List.of());
    }

    /**
     * Creates a new {@link DoubleArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link DoubleArgument}.
     */
    public static DoubleArgument of(String identifier, String argumentDescription, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new DoubleArgument(identifier, argumentDescription, null, isOptional, List.of(rules));
    }

    /**
     * Creates a new {@link DoubleArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link DoubleArgument}.
     */
    public static DoubleArgument of(String identifier, String argumentDescription, double defaultValue, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new DoubleArgument(identifier, argumentDescription, defaultValue, isOptional, List.of(rules));
    }

}
