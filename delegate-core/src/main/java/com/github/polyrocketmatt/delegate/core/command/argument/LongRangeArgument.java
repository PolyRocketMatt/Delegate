package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.range.DelegateLongRange;
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
public class LongRangeArgument extends CommandArgument<DelegateLongRange> {

    /**
     * Creates a new {@link LongRangeArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param isOptional Whether the argument is optional.
     * @param rules The rules of the argument.
     */
    private LongRangeArgument(String identifier, String argumentDescription, DelegateLongRange defaultValue, boolean isOptional, List<ArgumentRule<?>> rules) {
        super(identifier, argumentDescription, DelegateLongRange.class, new Argument<>(identifier, defaultValue), isOptional, rules);
    }

    @Override
    public @NotNull Argument<DelegateLongRange> parse(@Nullable String input) {
        if (input == null) {
            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a double range", DelegateLongRange.class);
            return getDefault();
        }

        try {
            String[] bounds = input.split("-");
            if (bounds.length != 2)
                if (getDefault().output() == null)
                    throw new ArgumentParseException("Expected integer range to contain only 2 bounds (min, max)", DelegateLongRange.class);
                else
                    return getDefault();

            long min = Long.parseLong(bounds[0]);
            long max = Long.parseLong(bounds[1]);

            return new Argument<>(getIdentifier(), new DelegateLongRange(min, max));
        } catch (NumberFormatException ex) {
            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a double range", DelegateLongRange.class);
            return getDefault();
        }
    }

    @Override
    public @NotNull DelegateLongRange parse(@NotNull StringReader reader) {
        int start = reader.getCursor();
        try {
            String[] bounds = reader.readString().split("-");
            if (bounds.length != 2)
                if (getDefault().output() == null)
                    throw new ArgumentParseException("Expected integer range to contain only 2 bounds (min, max)", DelegateLongRange.class);
                else
                    return getDefault().output();

            long min = Long.parseLong(bounds[0]);
            long max = Long.parseLong(bounds[1]);

            return new DelegateLongRange(min, max);
        } catch (NumberFormatException | CommandSyntaxException ex) {
            reader.setCursor(start);

            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a double range", DelegateLongRange.class);
            return getDefault().output();
        }
    }

    /**
     * Creates a new {@link LongRangeArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link LongRangeArgument}.
     */
    public static LongRangeArgument of(String identifier, String argumentDescription) {
        return new LongRangeArgument(identifier, argumentDescription, null, false, List.of());
    }

    /**
     * Creates a new {@link LongRangeArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The created {@link LongRangeArgument}.
     */
    public static LongRangeArgument of(String identifier, String argumentDescription, DelegateLongRange defaultValue) {
        return new LongRangeArgument(identifier, argumentDescription, defaultValue, false, List.of());
    }

    /**
     * Creates a new {@link LongRangeArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link LongRangeArgument}.
     */
    public static LongRangeArgument of(String identifier, String argumentDescription, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new LongRangeArgument(identifier, argumentDescription, null, isOptional, List.of(rules));
    }

    /**
     * Creates a new {@link LongRangeArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link LongRangeArgument}.
     */
    public static LongRangeArgument of(String identifier, String argumentDescription, DelegateLongRange defaultValue, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new LongRangeArgument(identifier, argumentDescription, defaultValue, isOptional, List.of(rules));
    }

}
