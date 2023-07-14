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
 * Represents a {@link CommandArgument} that parses an enum type from the input.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class EnumTypeArgument<T extends Enum<T>> extends CommandArgument<T>{

    private final Class<T> classType;

    /**
     * Creates a new {@link EnumTypeArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param isOptional Whether the argument is optional.
     * @param rules The rules of the argument.
     */
    private EnumTypeArgument(String identifier, String argumentDescription, T defaultValue, Class<T> classType, boolean isOptional, List<ArgumentRule<?>> rules) {
        super(identifier, argumentDescription, classType, new Argument<>(identifier, defaultValue), isOptional, rules);
        this.classType = classType;
    }

    /**
     * Gets the class type of the enum.
     *
     * @return The class type of the enum.
     */
    public Class<T> getClassType() {
        return classType;
    }

    @Override
    public @NotNull Argument<T> parse(@Nullable String input) {
        if (input == null) {
            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be of enum type " + classType.getSimpleName(), classType);
            return getDefault();
        }

        try {
            T[] enumConstants = classType.getEnumConstants();

            for (T enumConstant : enumConstants)
                if (enumConstant.name().equalsIgnoreCase(input))
                    return new Argument<>(getIdentifier(), enumConstant);
            throw new IllegalStateException("Enum constant not found");
        } catch (IllegalStateException ex) {
            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be of enum type" + classType.getSimpleName(), classType);
            return getDefault();
        }
    }

    @Override
    public @NotNull T parse(@NotNull StringReader reader) {
        int start = reader.getCursor();
        try {
            String value = reader.readString();
            T[] enumConstants = classType.getEnumConstants();

            for (T enumConstant : enumConstants)
                if (enumConstant.name().equalsIgnoreCase(value))
                    return enumConstant;
            throw new IllegalStateException("Enum constant not found");
        } catch (CommandSyntaxException | IllegalStateException ex) {
            reader.setCursor(start);

            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a double", Float.class);
            return getDefault().output();
        }
    }

    /**
     * Creates a new {@link EnumTypeArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link EnumTypeArgument}.
     */
    public static <T extends Enum<T>> EnumTypeArgument<T> of(String identifier, String argumentDescription, Class<T> classType) {
        return new EnumTypeArgument<>(identifier, argumentDescription, null, classType, false, List.of());
    }

    /**
     * Creates a new {@link EnumTypeArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The created {@link EnumTypeArgument}.
     */
    public static <T extends Enum<T>> EnumTypeArgument<T> of(String identifier, String argumentDescription, T defaultValue, Class<T> classType) {
        return new EnumTypeArgument<>(identifier, argumentDescription, defaultValue, classType, false, List.of());
    }

    /**
     * Creates a new {@link EnumTypeArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link EnumTypeArgument}.
     */
    public static <T extends Enum<T>> EnumTypeArgument<T> of(String identifier, String argumentDescription, Class<T> classType, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new EnumTypeArgument<>(identifier, argumentDescription, null, classType, isOptional, List.of(rules));
    }

    /**
     * Creates a new {@link EnumTypeArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link EnumTypeArgument}.
     */
    public static <T extends Enum<T>> EnumTypeArgument<T> of(String identifier, String argumentDescription, T defaultValue, Class<T> classType, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new EnumTypeArgument<>(identifier, argumentDescription, defaultValue, classType, isOptional, List.of(rules));
    }

}
