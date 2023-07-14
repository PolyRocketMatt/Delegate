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
 * Represents a {@link CommandArgument} that parses a boolean from the input.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class BoolArgument extends CommandArgument<Boolean> {

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
    private BoolArgument(String identifier, String argumentDescription, boolean defaultValue, boolean isOptional, List<ArgumentRule<?>> rules) {
        super(identifier, argumentDescription, Boolean.class, new Argument<>(identifier, defaultValue), isOptional, rules);
    }

    @Override
    public @NotNull Argument<Boolean> parse(@Nullable String input) {
        if (input == null) {
            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a boolean", Double.class);
            return getDefault();
        }

        try {
            return new Argument<>(getIdentifier(), Boolean.parseBoolean(input));
        } catch (NumberFormatException ex) {
            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a boolean", Double.class);
            return getDefault();
        }
    }

    @Override
    public @NotNull Boolean parse(@NotNull StringReader reader) {
        int start = reader.getCursor();
        try {
            return reader.readBoolean();
        } catch (CommandSyntaxException ex) {
            reader.setCursor(start);

            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a boolean", Float.class);
            return getDefault().output();
        }
    }

    /**
     * Creates a new {@link BoolArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link BoolArgument}.
     */
    public static BoolArgument of(String identifier, String argumentDescription) {
        return new BoolArgument(identifier, argumentDescription, false, false, List.of());
    }

    /**
     * Creates a new {@link BoolArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The created {@link BoolArgument}.
     */
    public static BoolArgument of(String identifier, String argumentDescription, boolean defaultValue) {
        return new BoolArgument(identifier, argumentDescription, defaultValue, false, List.of());
    }

    /**
     * Creates a new {@link BoolArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link BoolArgument}.
     */
    public static BoolArgument of(String identifier, String argumentDescription, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new BoolArgument(identifier, argumentDescription, false, isOptional, List.of(rules));
    }

    /**
     * Creates a new {@link BoolArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link BoolArgument}.
     */
    public static BoolArgument of(String identifier, String argumentDescription, boolean defaultValue, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new BoolArgument(identifier, argumentDescription, defaultValue, isOptional, List.of(rules));
    }

}
