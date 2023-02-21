// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.impl.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class WorldArgument extends CommandArgument<World> {

    /**
     * Creates a new {@link WorldArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param isOptional Whether the argument is optional or not.
     * @param rules The rules of the argument.
     */
    private WorldArgument(String identifier, String argumentDescription, World defaultValue, boolean isOptional, List<ArgumentRule<?>> rules) {
        super(identifier, argumentDescription, World.class, new Argument<>(identifier, defaultValue), isOptional, rules);
    }

    @Override
    public @NotNull Argument<World> parse(@NotNull String input) {
        World world = Bukkit.getWorld(input);

        return (world != null) ? new Argument<>(getIdentifier(), world) : getDefault();
    }

    @Override
    public @NotNull World parse(@NotNull StringReader reader) {
        int start = reader.getCursor();
        try {
            World world = Bukkit.getWorld(reader.readString());

            return (world != null) ? world : getDefault().output();
        } catch (CommandSyntaxException e) {
            reader.setCursor(start);

            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a player", Player.class);
            return getDefault().output();
        }
    }

    /**
     * Creates a new {@link WorldArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link WorldArgument}.
     */
    public static WorldArgument of(String identifier, String argumentDescription) {
        return new WorldArgument(identifier, argumentDescription, null, false, List.of());
    }

    /**
     * Creates a new {@link WorldArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The created {@link WorldArgument}.
     */
    public static WorldArgument of(String identifier, String argumentDescription, World defaultValue) {
        return new WorldArgument(identifier, argumentDescription, defaultValue, false, List.of());
    }

    /**
     * Creates a new {@link WorldArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link WorldArgument}.
     */
    public static WorldArgument of(String identifier, String argumentDescription, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new WorldArgument(identifier, argumentDescription, null, isOptional, List.of(rules));
    }

    /**
     * Creates a new {@link WorldArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link WorldArgument}.
     */
    public static WorldArgument of(String identifier, String argumentDescription, World defaultValue, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new WorldArgument(identifier, argumentDescription, defaultValue, isOptional, List.of(rules));
    }

}
