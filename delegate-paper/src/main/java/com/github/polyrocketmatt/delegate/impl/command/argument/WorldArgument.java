package com.github.polyrocketmatt.delegate.impl.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.DefaultRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.github.polyrocketmatt.delegate.core.utils.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;
import java.util.function.Consumer;

public class WorldArgument extends CommandArgument<World> {

    /**
     * Creates a new {@link WorldArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     */
    private WorldArgument(String identifier, String argumentDescription) {
        super(identifier, argumentDescription, World.class, new NonNullRule());
    }

    /**
     * Creates a new {@link WorldArgument} with an identifier, a description and a
     * default value.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     */
    private WorldArgument(String identifier, String argumentDescription, World defaultValue) {
        super(identifier, argumentDescription, World.class, new DefaultRule<>(defaultValue));
    }

    /**
     * Creates a new {@link WorldArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rule The rule of the argument.
     */
    private WorldArgument(String identifier, String argumentDescription, ArgumentRule<?> rule) {
        super(identifier, argumentDescription, World.class, rule);
    }

    /**
     * Creates a new {@link WorldArgument} with an identifier, a description, a default value and a
     * list of {@link ArgumentRule}s.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param defaultValue The default value of the argument.
     * @param rules The rules of the argument.
     */
    private WorldArgument(String identifier, String argumentDescription, World defaultValue, ArgumentRule<?> rules) {
        super(identifier, argumentDescription, World.class, ArrayUtils.combine(List.of(new DefaultRule<>(defaultValue)), List.of(rules)));
    }

    @Override
    public Argument<World> parse(String input, Consumer<Exception> consumer) {
        World world = Bukkit.getWorld(input);

        return (world != null) ? new Argument<>(getIdentifier(), world) : getDefault();
    }

    /**
     * Creates a new {@link WorldArgument} with an identifier and a description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @return The created {@link WorldArgument}.
     */
    public static WorldArgument of(String identifier, String argumentDescription) {
        return new WorldArgument(identifier, argumentDescription);
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
        return new WorldArgument(identifier, argumentDescription, defaultValue);
    }

    /**
     * Creates a new {@link WorldArgument} with an identifier, a description and an {@link ArgumentRule}.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param rule The rule of the argument.
     * @return The created {@link WorldArgument}.
     */
    public static WorldArgument of(String identifier, String argumentDescription, ArgumentRule<?> rule) {
        return new WorldArgument(identifier, argumentDescription, rule);
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
    public static WorldArgument of(String identifier, String argumentDescription, World defaultValue, ArgumentRule<?> rules) {
        return new WorldArgument(identifier, argumentDescription, defaultValue, rules);
    }

}

