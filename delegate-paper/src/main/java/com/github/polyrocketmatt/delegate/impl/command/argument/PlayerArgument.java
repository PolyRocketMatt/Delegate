// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.impl.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.github.polyrocketmatt.delegate.core.utils.ArrayUtils;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

public class PlayerArgument extends CommandArgument<Player> {

    /**
     * Creates a new {@link PlayerArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     */
    private PlayerArgument(String identifier, String argumentDescription) {
        super(identifier, argumentDescription, Player.class, new NonNullRule());
    }

    /**
     * Creates a new {@link PlayerArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     */
    private PlayerArgument(String identifier, String argumentDescription, Player defaultValue) {
        super(identifier, argumentDescription, Player.class, new DefaultRule<>(defaultValue));
    }

    /**
     * Creates a new {@link PlayerArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rule The rule of the argument.
     */
    private PlayerArgument(String identifier, String argumentDescription, ArgumentRule<?> rule) {
        super(identifier, argumentDescription, Player.class, rule);
    }

    /**
     * Creates a new {@link PlayerArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     */
    private PlayerArgument(String identifier, String argumentDescription, Player defaultValue, ArgumentRule<?> rules) {
        super(identifier, argumentDescription, Player.class, ArrayUtils.combine(List.of(new DefaultRule<>(defaultValue)), List.of(rules)));
    }

    @Override
    public Argument<Player> parse(String input, Consumer<Exception> consumer) {
        Player player = Bukkit.getPlayer(input);

        return (player != null) ? new Argument<>(getIdentifier(), player) : getDefault();
    }

    @Override
    public Player parse(StringReader reader) throws CommandSyntaxException {
        Player player = Bukkit.getPlayer(reader.readString());

        return (player != null) ? player : getDefault().output();
    }

    /**
     * Creates a new {@link PlayerArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link PlayerArgument}.
     */
    public static PlayerArgument of(String identifier, String argumentDescription) {
        return new PlayerArgument(identifier, argumentDescription);
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
        return new PlayerArgument(identifier, argumentDescription, defaultValue);
    }

    /**
     * Creates a new {@link PlayerArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rule The rule of the argument.
     * @return The created {@link PlayerArgument}.
     */
    public static PlayerArgument of(String identifier, String argumentDescription, ArgumentRule<?> rule) {
        return new PlayerArgument(identifier, argumentDescription, rule);
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
    public static PlayerArgument of(String identifier, String argumentDescription, Player defaultValue, ArgumentRule<?> rules) {
        return new PlayerArgument(identifier, argumentDescription, defaultValue, rules);
    }

}
