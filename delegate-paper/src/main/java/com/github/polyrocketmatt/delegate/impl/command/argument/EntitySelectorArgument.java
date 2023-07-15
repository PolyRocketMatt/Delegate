// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.impl.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntitySelectorArgument<T extends Entity> extends CommandArgument<List<T>> {

    private final Class<T> entityType;

    /**
     * Creates a new {@link EntitySelectorArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param isOptional Whether the argument is optional or not.
     * @param rules The rules of the argument.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private EntitySelectorArgument(String identifier, String argumentDescription, Class<T> entityType, List<T> defaultValue,
                                   boolean isOptional, List<ArgumentRule<?>> rules) {
        super(identifier, argumentDescription,  (Class<List<T>>) ((Class) List.class), new Argument<>(identifier, defaultValue), isOptional, rules);
        this.entityType = entityType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull Argument<List<T>> parse(@Nullable String input) {
        if (input == null)
            return getDefault();

        try {
            if (input.startsWith("@")) {
                List<T> entities = new ArrayList<>();
                Bukkit.selectEntities(Bukkit.getConsoleSender(), input).forEach(entity -> {
                    if (entityType.isInstance(entity))
                        entities.add((T) entity);
                });

                return new Argument<>(getIdentifier(), entities);
            } else {
                throw new CommandSyntaxException(new CommandExceptionType() {
                }, () -> "Expected Bukkit-style entity selector, got " + input);
            }
        } catch (CommandSyntaxException ex) {
            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a float", Float.class);
            return getDefault();
        }
    }

    @Override
    public @NotNull List<T> parse(@NotNull StringReader reader) {
        throw new UnsupportedOperationException("This method is not supported for this argument type");
    }

    /**
     * Creates a new {@link EntitySelectorArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param entityType The type of entity to select.
     * @return The created {@link EntitySelectorArgument}.
     */
    public static <T extends Entity> EntitySelectorArgument<T> of(String identifier, String argumentDescription, Class<T> entityType) {
        return new EntitySelectorArgument<>(identifier, argumentDescription, entityType, null, false, List.of());
    }

    /**
     * Creates a new {@link EntitySelectorArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param entityType The type of entity to select.
     * @return The created {@link EntitySelectorArgument}.
     */
    public static <T extends Entity> EntitySelectorArgument<T> of(String identifier, String argumentDescription, Class<T> entityType, List<T> defaultValue) {
        return new EntitySelectorArgument<>(identifier, argumentDescription, entityType, defaultValue, false, List.of());
    }

    /**
     * Creates a new {@link EntitySelectorArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param entityType The type of entity to select.
     * @param rules The rules of the argument.
     * @return The created {@link EntitySelectorArgument}.
     */
    public static <T extends Entity> EntitySelectorArgument<T> of(String identifier, String argumentDescription, Class<T> entityType, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new EntitySelectorArgument<>(identifier, argumentDescription, entityType, null, isOptional, List.of(rules));
    }

    /**
     * Creates a new {@link EntitySelectorArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param entityType The type of entity to select.
     * @param rules The rules of the argument.
     * @return The created {@link EntitySelectorArgument}.
     */
    public static <T extends Entity> EntitySelectorArgument<T> of(String identifier, String argumentDescription, Class<T> entityType, List<T> defaultValue, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new EntitySelectorArgument<>(identifier, argumentDescription, entityType, defaultValue, isOptional, List.of(rules));
    }

}
