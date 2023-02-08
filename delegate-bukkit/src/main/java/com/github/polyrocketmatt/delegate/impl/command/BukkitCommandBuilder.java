package com.github.polyrocketmatt.delegate.impl.command;

import com.github.polyrocketmatt.delegate.api.TriConsumer;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.ICommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.ICommandBuilder;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.Context;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.AttributeException;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommandBuilder;
import com.github.polyrocketmatt.delegate.core.command.action.ConsumerAction;
import com.github.polyrocketmatt.delegate.core.command.action.ExceptAction;
import com.github.polyrocketmatt.delegate.core.command.action.FunctionAction;
import com.github.polyrocketmatt.delegate.core.command.action.RunnableAction;
import com.github.polyrocketmatt.delegate.core.command.action.SupplierAction;
import com.github.polyrocketmatt.delegate.core.command.argument.FloatArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.IntArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.StringArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.AliasDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.properties.AsyncProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.CatchExceptionProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNonPresentProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;
import com.github.polyrocketmatt.delegate.core.command.trigger.FailureTrigger;
import com.github.polyrocketmatt.delegate.core.command.trigger.SuccessTrigger;
import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTiers;
import com.github.polyrocketmatt.delegate.core.command.permission.StandardPermission;
import com.github.polyrocketmatt.delegate.impl.command.argument.PlayerArgument;
import com.github.polyrocketmatt.delegate.impl.command.argument.WorldArgument;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
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
     * Append a new alias for the command.
     *
     * @param alias The alias to append.
     * @return The current chain.
     */
    @Override
    public ICommandBuilder withAlias(String alias) {
        return this.with(new AliasDefinition(alias));
    }

    /**
     * Append new aliases for the command.
     *
     * @param aliases The aliases to append.
     * @return The current chain.
     */
    @Override
    public ICommandBuilder withAliases(String... aliases) {
        for (String alias : aliases)
            this.withAlias(alias);
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
    public BukkitCommandBuilder withIgnoreNonPresent() {
        return this.with(new IgnoreNonPresentProperty());
    }

    @Override
    public BukkitCommandBuilder withExceptionCatching() {
        return this.with(new CatchExceptionProperty());
    }

    @Override
    public BukkitCommandBuilder withPermission(PermissionTier tier) {
        return this.with(tier);
    }

    @Override
    public BukkitCommandBuilder withPermission(String permission, PermissionTier parent) {
        return this.with(new StandardPermission(permission, parent));
    }

    @Override
    public BukkitCommandBuilder withPermission(String permission) {
        return this.withPermission(new StandardPermission(permission));
    }

    @Override
    public BukkitCommandBuilder withOperatorPermission() {
        return this.withPermission(PermissionTiers.OPERATOR.getTier());
    }

    @Override
    public BukkitCommandBuilder withGlobalPermission() {
        return this.withPermission(PermissionTiers.GLOBAL.getTier());
    }

    public BukkitCommandBuilder withConsumerAction(BiConsumer<CommanderEntity, Context> action) {
        return this.with(new ConsumerAction(action));
    }

    public BukkitCommandBuilder withConsumerAction(String identifier, BiConsumer<CommanderEntity, Context> action) {
        return this.with(new ConsumerAction(identifier, action));
    }

    public BukkitCommandBuilder withFunctionAction(BiFunction<CommanderEntity, Context, ?> action) {
        return this.with(new FunctionAction(action));
    }

    public BukkitCommandBuilder withFunctionAction(String identifier, BiFunction<CommanderEntity, Context, ?> action) {
        return this.with(new FunctionAction(identifier, action));
    }

    public BukkitCommandBuilder withRunnableAction(Runnable action) {
        return this.with(new RunnableAction(action));
    }

    public BukkitCommandBuilder withRunnableAction(String identifier, Runnable action) {
        return this.with(new RunnableAction(identifier, action));
    }

    public <T> BukkitCommandBuilder withSupplierAction(Supplier<T> action) {
        return this.with(new SupplierAction<>(action));
    }

    public <T> BukkitCommandBuilder withSupplierAction(String identifier, Supplier<T> action) {
        return this.with(new SupplierAction<>(identifier, action));
    }

    public BukkitCommandBuilder onExcept(TriConsumer<CommanderEntity, FeedbackType, List<String>> action) {
        return this.with(new ExceptAction(action));
    }

    public BukkitCommandBuilder onSucces(BiConsumer<CommandDispatchInformation, CommandCapture> onSuccess) {
        return this.with(new SuccessTrigger(onSuccess));
    }

    public BukkitCommandBuilder onFail(BiConsumer<CommandDispatchInformation, CommandCapture> onFail) {
        return this.with(new FailureTrigger(onFail));
    }

    /**
     * Append a new {@link PlayerArgument} to the chain with the given description.
     *
     * @param name The name of the argument.
     * @param description The description of the argument.
     * @return The current chain.
     */
    public BukkitCommandBuilder withPlayer(String name, String description) {
        return this.with(PlayerArgument.of(name, description));
    }

    /**
     * Append a new {@link PlayerArgument} to the chain with the given description and default value.
     *
     * @param name The name of the argument.
     * @param description The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The current chain.
     */
    public BukkitCommandBuilder withPlayer(String name, String description, Player defaultValue) {
        return this.with(PlayerArgument.of(name, description, defaultValue));
    }

    /**
     * Append a new {@link WorldArgument} to the chain with the given description.
     *
     * @param name The name of the argument.
     * @param description The description of the argument.
     * @return The current chain.
     */
    public BukkitCommandBuilder withWorld(String name, String description) {
        return this.with(WorldArgument.of(name, description));
    }

    /**
     * Append a new {@link WorldArgument} to the chain with the given description and default value.
     *
     * @param name The name of the argument.
     * @param description The description of the argument.
     * @param defaultValue The default value of the argument.
     * @return The current chain.
     */
    public BukkitCommandBuilder withWorld(String name, String description, World defaultValue) {
        return this.with(WorldArgument.of(name, description, defaultValue));
    }

}
