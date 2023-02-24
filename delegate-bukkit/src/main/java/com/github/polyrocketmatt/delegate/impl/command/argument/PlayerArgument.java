// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.impl.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.github.polyrocketmatt.delegate.core.utils.ArrayUtils;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class PlayerArgument extends CommandArgument<Player> {

    /**
     * Creates a new {@link PlayerArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param isOptional Whether the argument is optional or not.
     * @param rules The rules of the argument.
     */
    private PlayerArgument(String identifier, String argumentDescription, Player defaultValue, boolean isOptional, List<ArgumentRule<?>> rules) {
        super(identifier, argumentDescription, Player.class, new Argument<>(identifier, defaultValue), isOptional, rules);
    }

    @Override
    public @NotNull Argument<Player> parse(@NotNull String input) {
        Player player = Bukkit.getPlayer(input);

        return (player != null) ? new Argument<>(getIdentifier(), player) : getDefault();
    }

    @Override
    public @NotNull Player parse(@NotNull StringReader reader) {
        int start = reader.getCursor();
        try {
            Player player = Bukkit.getPlayer(reader.readString());

            return (player != null) ? player : getDefault().output();
        } catch (CommandSyntaxException ex) {
            reader.setCursor(start);

            if (getDefault().output() == null)
                throw new ArgumentParseException("The argument '" + getIdentifier() + "' must be a player", Player.class);
            return getDefault().output();
        }
    }

    /**
     * Creates a new {@link PlayerArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link PlayerArgument}.
     */
    public static PlayerArgument of(String identifier, String argumentDescription) {
        return new PlayerArgument(identifier, argumentDescription, null, false, List.of());
    }

    /**
     * Creates a new {@link PlayerArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The created {@link PlayerArgument}.
     */
    public static PlayerArgument of(String identifier, String argumentDescription, Player defaultValue) {
        return new PlayerArgument(identifier, argumentDescription, defaultValue, false, List.of());
    }

    /**
     * Creates a new {@link PlayerArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link PlayerArgument}.
     */
    public static PlayerArgument of(String identifier, String argumentDescription, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new PlayerArgument(identifier, argumentDescription, null, isOptional, List.of(rules));
    }

    /**
     * Creates a new {@link PlayerArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     * @return The created {@link PlayerArgument}.
     */
    public static PlayerArgument of(String identifier, String argumentDescription, Player defaultValue, ArgumentRule<?>... rules) {
        boolean isOptional = Arrays.stream(rules).noneMatch(rule -> rule instanceof NonNullRule);
        return new PlayerArgument(identifier, argumentDescription, defaultValue, isOptional, List.of(rules));
    }

}
