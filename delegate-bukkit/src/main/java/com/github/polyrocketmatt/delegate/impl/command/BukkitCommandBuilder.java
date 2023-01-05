package com.github.polyrocketmatt.delegate.impl.command;

import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.ICommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.ICommandBuilder;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.Index;
import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.AttributeException;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommandBuilder;
import com.github.polyrocketmatt.delegate.core.command.action.ConsumerAction;
import com.github.polyrocketmatt.delegate.core.command.action.FunctionAction;
import com.github.polyrocketmatt.delegate.core.command.action.RunnableAction;
import com.github.polyrocketmatt.delegate.core.command.action.SupplierAction;
import com.github.polyrocketmatt.delegate.core.command.argument.FloatArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.IntArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.StringArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.properties.AsyncProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.BrigadierProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNonPresentProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class BukkitCommandBuilder extends DelegateCommandBuilder {

    public BukkitCommandBuilder() {
        super();
    }

    /**
     * Append a new {@link CommandAttribute} to the chain.
     *
     * @param attribute The attribute to append.
     * @return The current chain.
     * @throws AttributeException If the attribute is a {@link CommandAction} whose precedence is less than 0.
     */
    @Override
    public BukkitCommandBuilder with(ICommandAttribute attribute) {
        if (attribute instanceof CommandAction && ((CommandAction) attribute).getPrecedence() < 0)
            throw new AttributeException("Action precedence must be greater than 0");
        this.attributes.add((CommandAttribute) attribute);
        return this;
    }

    /**
     * Append a new {@link CommandAction} to the chain.
     *
     * @param action The action to append.
     * @return The current chain.
     * @throws AttributeException If the action's precedence is less than 0.
     */
    @Override
    public BukkitCommandBuilder withAction(CommandAction action) {
        //  Check that action precedence is greater than or equal to 0
        if (action.getPrecedence() <= 0)
            throw new AttributeException("Action precedence must be greater than 0");
        return this.with(action);
    }

    /**
     * Append a new {@link CommandArgument} to the chain.
     *
     * @param argument The argument to append.
     * @return The current chain.
     */
    @Override
    public BukkitCommandBuilder withArgument(CommandArgument<?> argument) {
        return this.with(argument);
    }

    /**
     * Append a new {@link FloatArgument} to the chain with the given description.
     *
     * @param name The name of the argument.
     * @param description The description of the argument.
     * @return The current chain.
     */
    @Override
    public BukkitCommandBuilder withFloat(String name, String description) {
        return this.with(FloatArgument.of(name, description));
    }

    /**
     * Append a new {@link FloatArgument} to the chain with the given description and default value.
     *
     * @param name The name of the argument.
     * @param description The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The current chain.
     */
    @Override
    public BukkitCommandBuilder withFloat(String name, String description, float defaultValue) {
        return this.with(FloatArgument.of(name, description, defaultValue));
    }

    /**
     * Append a new {@link IntArgument} to the chain with the given description.
     *
     * @param name The name of the argument.
     * @param description The description of the argument.
     * @return The current chain.
     */
    @Override
    public BukkitCommandBuilder withInt(String name, String description) {
        return this.with(IntArgument.of(name, description));
    }

    /**
     * Append a new {@link IntArgument} to the chain with the given description and default value.
     *
     * @param name The name of the argument.
     * @param description The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The current chain.
     */
    @Override
    public BukkitCommandBuilder withInt(String name, String description, int defaultValue) {
        return this.with(IntArgument.of(name, description, defaultValue));
    }

    /**
     * Append a new {@link StringArgument} to the chain with the given description.
     *
     * @param name The name of the argument.
     * @param description The description of the argument.
     * @return The current chain.
     */
    @Override
    public BukkitCommandBuilder withString(String name, String description) {
        return this.with(StringArgument.of(name, description));
    }

    /**
     * Append a new {@link StringArgument} to the chain with the given description and default value.
     *
     * @param name The name of the argument.
     * @param description The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The current chain.
     */
    @Override
    public BukkitCommandBuilder withString(String name, String description, String defaultValue) {
        return this.with(StringArgument.of(name, description, defaultValue));
    }

    /**
     * Append a new {@link CommandDefinition} to the chain.
     *
     * @param definition The definition to append.
     * @return The current chain.
     */
    @Override
    public BukkitCommandBuilder withDefinition(CommandDefinition<?> definition) {
        return this.with(definition);
    }

    /**
     * Append a new {@link SubcommandDefinition} to the chain.
     *
     * @param subcommand The subcommand to append.
     * @return The current chain.
     */
    @Override
    public BukkitCommandBuilder withSubcommand(CommandDefinition<?> subcommand) {
        if (!(subcommand instanceof SubcommandDefinition))
            throw new AttributeException("Subcommand must be a SubcommandDefinition");
        return this.with(subcommand);
    }

    /**
     * Append a new {@link CommandProperty} to the chain.
     *
     * @param property The property to append.
     * @return The current chain.
     */
    @Override
    public BukkitCommandBuilder withProperty(CommandProperty property) {
        return this.with(property);
    }

    /**
     * Append a new {@link AsyncProperty} to the chain.
     *
     * @return The current chain.
     */
    @Override
    public BukkitCommandBuilder withAsync() {
        return this.with(new AsyncProperty());
    }

    /**
     * Append a new {@link BrigadierProperty} to the chain.
     *
     * @return The current chain.
     */
    @Override
    public BukkitCommandBuilder withBrigadier() {
        return this.with(new BrigadierProperty());
    }

    /**
     * Append a new {@link IgnoreNullProperty} to the chain.
     *
     * @return The current chain.
     */
    @Override
    public BukkitCommandBuilder withIgnoreNull() {
        return this.with(new IgnoreNullProperty());
    }

    /**
     * Append a new {@link IgnoreNonPresentProperty} to the chain.
     *
     * @return The current chain.
     */
    @Override
    public ICommandBuilder withIgnoreNonPresent() {
        return this.with(new IgnoreNonPresentProperty());
    }

    public BukkitCommandBuilder withAction(BiConsumer<CommanderEntity, Index> action) {
        return this.with(new ConsumerAction(action));
    }

    public BukkitCommandBuilder withAction(String identifier, BiConsumer<CommanderEntity, Index> action) {
        return this.with(new ConsumerAction(identifier, action));
    }

    public BukkitCommandBuilder withAction(BiFunction<CommanderEntity, Index, ?> action) {
        return this.with(new FunctionAction(action));
    }

    public BukkitCommandBuilder withAction(String identifier, BiFunction<CommanderEntity, Index, ?> action) {
        return this.with(new FunctionAction(identifier, action));
    }

    public BukkitCommandBuilder withAction(Runnable action) {
        return this.with(new RunnableAction(action));
    }

    public BukkitCommandBuilder withAction(String identifier, Runnable action) {
        return this.with(new RunnableAction(identifier, action));
    }

    public <T> BukkitCommandBuilder withAction(Supplier<T> action) {
        return this.with(new SupplierAction<>(action));
    }

    public <T> BukkitCommandBuilder withAction(String identifier, Supplier<T> action) {
        return this.with(new SupplierAction<>(identifier, action));
    }

}
