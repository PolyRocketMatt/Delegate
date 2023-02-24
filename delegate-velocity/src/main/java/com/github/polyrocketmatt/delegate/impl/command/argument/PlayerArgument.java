// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.impl.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.github.polyrocketmatt.delegate.impl.Delegate;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Optional<Player> player = Delegate.getProxy().getPlayer(input);

        return player.map(value -> new Argument<>(getIdentifier(), value)).orElseGet(this::getDefault);
    }

    @Override
    public @NotNull Player parse(@NotNull StringReader reader) {
        int start = reader.getCursor();
        try {
            Optional<Player> player = Delegate.getProxy().getPlayer(reader.readString());

            return player.orElseGet(() -> getDefault().output());
        } catch (CommandSyntaxException e) {
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