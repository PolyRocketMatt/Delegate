package com.github.polyrocketmatt.delegate.impl.command;

import com.github.polyrocketmatt.delegate.api.TriConsumer;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.ICommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.ICommandBuilder;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.Index;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.AttributeException;
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
import com.github.polyrocketmatt.delegate.core.command.properties.BrigadierProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.CatchExceptionProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNonPresentProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;
import com.github.polyrocketmatt.delegate.core.command.trigger.FailureTrigger;
import com.github.polyrocketmatt.delegate.core.command.trigger.SuccessTrigger;
import com.github.polyrocketmatt.delegate.core.permission.PermissionTiers;
import com.github.polyrocketmatt.delegate.core.permission.StandardPermission;
import com.github.polyrocketmatt.delegate.impl.command.argument.PlayerArgument;
import com.velocitypowered.api.proxy.Player;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class VelocityCommandBuilder extends DelegateCommandBuilder {

    public VelocityCommandBuilder() {
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
    public VelocityCommandBuilder with(ICommandAttribute attribute) {
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
    public VelocityCommandBuilder withAction(CommandAction action) {
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
    public VelocityCommandBuilder withArgument(CommandArgument<?> argument) {
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
    public VelocityCommandBuilder withFloat(String name, String description) {
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
    public VelocityCommandBuilder withFloat(String name, String description, float defaultValue) {
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
    public VelocityCommandBuilder withInt(String name, String description) {
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
    public VelocityCommandBuilder withInt(String name, String description, int defaultValue) {
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
    public VelocityCommandBuilder withString(String name, String description) {
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
    public VelocityCommandBuilder withString(String name, String description, String defaultValue) {
        return this.with(StringArgument.of(name, description, defaultValue));
    }

    /**
     * Append a new {@link CommandDefinition} to the chain.
     *
     * @param definition The definition to append.
     * @return The current chain.
     */
    @Override
    public VelocityCommandBuilder withDefinition(CommandDefinition<?> definition) {
        return this.with(definition);
    }

    /**
     * Append a new {@link SubcommandDefinition} to the chain.
     *
     * @param subcommand The subcommand to append.
     * @return The current chain.
     */
    @Override
    public VelocityCommandBuilder withSubcommand(CommandDefinition<?> subcommand) {
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
    public VelocityCommandBuilder withProperty(CommandProperty property) {
        return this.with(property);
    }

    /**
     * Append a new {@link AsyncProperty} to the chain.
     *
     * @return The current chain.
     */
    @Override
    public VelocityCommandBuilder withAsync() {
        return this.with(new AsyncProperty());
    }

    /**
     * Append a new {@link BrigadierProperty} to the chain.
     *
     * @return The current chain.
     */
    @Override
    public VelocityCommandBuilder withBrigadier() {
        return this.with(new BrigadierProperty());
    }

    /**
     * Append a new {@link IgnoreNullProperty} to the chain.
     *
     * @return The current chain.
     */
    @Override
    public VelocityCommandBuilder withIgnoreNull() {
        return this.with(new IgnoreNullProperty());
    }

    /**
     * Append a new {@link IgnoreNonPresentProperty} to the chain.
     *
     * @return The current chain.
     */
    @Override
    public VelocityCommandBuilder withIgnoreNonPresent() {
        return this.with(new IgnoreNonPresentProperty());
    }

    @Override
    public VelocityCommandBuilder withExceptionCatching() {
        return this.with(new CatchExceptionProperty());
    }

    @Override
    public VelocityCommandBuilder withPermission(PermissionTier tier) {
        return this.with(tier);
    }

    @Override
    public VelocityCommandBuilder withPermission(String permission, PermissionTier parent) {
        return this.with(new StandardPermission(permission, parent));
    }

    @Override
    public VelocityCommandBuilder withPermission(String permission) {
        return this.withPermission(new StandardPermission(permission));
    }

    @Override
    public VelocityCommandBuilder withOperatorPermission() {
        return this.withPermission(PermissionTiers.OPERATOR.getTier());
    }

    @Override
    public VelocityCommandBuilder withGlobalPermission() {
        return this.withPermission(PermissionTiers.GLOBAL.getTier());
    }

    public VelocityCommandBuilder withConsumerAction(BiConsumer<CommanderEntity, Index> action) {
        return this.with(new ConsumerAction(action));
    }

    public VelocityCommandBuilder withConsumerAction(String identifier, BiConsumer<CommanderEntity, Index> action) {
        return this.with(new ConsumerAction(identifier, action));
    }

    public VelocityCommandBuilder withFunctionAction(BiFunction<CommanderEntity, Index, ?> action) {
        return this.with(new FunctionAction(action));
    }

    public VelocityCommandBuilder withFunctionAction(String identifier, BiFunction<CommanderEntity, Index, ?> action) {
        return this.with(new FunctionAction(identifier, action));
    }

    public VelocityCommandBuilder withRunnableAction(Runnable action) {
        return this.with(new RunnableAction(action));
    }

    public VelocityCommandBuilder withRunnableAction(String identifier, Runnable action) {
        return this.with(new RunnableAction(identifier, action));
    }

    public <T> VelocityCommandBuilder withSupplierAction(Supplier<T> action) {
        return this.with(new SupplierAction<>(action));
    }

    public <T> VelocityCommandBuilder withSupplierAction(String identifier, Supplier<T> action) {
        return this.with(new SupplierAction<>(identifier, action));
    }

    public VelocityCommandBuilder onExcept(TriConsumer<CommanderEntity, FeedbackType, List<String>> action) {
        return this.with(new ExceptAction(action));
    }

    public VelocityCommandBuilder onSucces(BiConsumer<CommandDispatchInformation, CommandCapture> onSuccess) {
        return this.with(new SuccessTrigger(onSuccess));
    }

    public VelocityCommandBuilder onFail(BiConsumer<CommandDispatchInformation, CommandCapture> onFail) {
        return this.with(new FailureTrigger(onFail));
    }

    /**
     * Append a new {@link PlayerArgument} to the chain with the given description.
     *
     * @param name The name of the argument.
     * @param description The description of the argument.
     * @return The current chain.
     */
    public VelocityCommandBuilder withPlayer(String name, String description) {
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
    public VelocityCommandBuilder withPlayer(String name, String description, Player defaultValue) {
        return this.with(PlayerArgument.of(name, description, defaultValue));
    }

}
