package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.range.DelegateDoubleRange;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a {@link CommandArgument} that parses a long from the input.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class DoubleRangeArgument extends CommandArgument<DelegateDoubleRange> {

    /**
     * Creates a new {@link DoubleRangeArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param isOptional Whether the argument is optional.
     * @param rules The rules of the argument.
     */
    private DoubleRangeArgument(String identifier, String argumentDescription, DelegateDoubleRange defaultValue, boolean isOptional, List<ArgumentRule<?>> rules) {
        super(identifier, argumentDescription, DelegateDoubleRange.class, new Argument<>(identifier, defaultValue), isOptional, rules);
    }

    @Override
    public @NotNull Argument<DelegateDoubleRange> parse(@Nullable String input) {
        if (input == null) {
            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a double range", DelegateDoubleRange.class);
            return getDefault();
        }

        try {
            String[] bounds = input.split("-");
            if (bounds.length != 2)
                if (getDefault().output() == null)
                    throw new ArgumentParseException("Expected integer range to contain only 2 bounds (min, max)", DelegateDoubleRange.class);
                else
                    return getDefault();

            double min = Double.parseDouble(bounds[0]);
            double max = Double.parseDouble(bounds[1]);

            return new Argument<>(getIdentifier(), new DelegateDoubleRange(min, max));
        } catch (NumberFormatException ex) {
            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a double range", DelegateDoubleRange.class);
            return getDefault();
        }
    }

    @Override
    public @NotNull DelegateDoubleRange parse(@NotNull StringReader reader) {
        int start = reader.getCursor();
        try {
            String[] bounds = reader.readString().split("-");
            if (bounds.length != 2)
                if (getDefault().output() == null)
                    throw new ArgumentParseException("Expected integer range to contain only 2 bounds (min, max)", DelegateDoubleRange.class);
                else
                    return getDefault().output();

            double min = Double.parseDouble(bounds[0]);
            double max = Double.parseDouble(bounds[1]);

            return new DelegateDoubleRange(min, max);
        } catch (NumberFormatException | CommandSyntaxException ex) {
            reader.setCursor(start);

            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a double range", DelegateDoubleRange.class);
            return getDefault().output();
        }
    }

    /**
     * Creates a new {@link DoubleRangeArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link DoubleRangeArgument}.
     */
    public static DoubleRangeArgument of(String identifier, String argumentDescription) {
        return new DoubleRangeArgument(identifier, argumentDescription, null, false, List.of());
    }

    /**
     * Creates a new {@link DoubleRangeArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The created {@link DoubleRangeArgument}.
     */
    public static DoubleRangeArgument of(String identifier, String argumentDescription, DelegateDoubleRange defaultValue) {
        return new DoubleRangeArgument(identifier, argumentDescription, defaultValue, false, List.of());
    }

    /**
     * Creates a new {@link DoubleRangeArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link DoubleRangeArgument}.
     */
    public static DoubleRangeArgument of(String identifier, String argumentDescription, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new DoubleRangeArgument(identifier, argumentDescription, null, isOptional, List.of(rules));
    }

    /**
     * Creates a new {@link DoubleRangeArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link DoubleRangeArgument}.
     */
    public static DoubleRangeArgument of(String identifier, String argumentDescription, DelegateDoubleRange defaultValue, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new DoubleRangeArgument(identifier, argumentDescription, defaultValue, isOptional, List.of(rules));
    }

}
