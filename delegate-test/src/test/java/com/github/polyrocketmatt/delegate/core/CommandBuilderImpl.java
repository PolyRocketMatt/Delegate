// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core;

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
import com.github.polyrocketmatt.delegate.core.command.argument.BoolArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.DoubleArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.FloatArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.IntArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.LongArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.StringArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.AliasDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTierType;
import com.github.polyrocketmatt.delegate.core.command.permission.StandardPermission;
import com.github.polyrocketmatt.delegate.core.command.properties.AsyncProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.CatchExceptionProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNonPresentProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;
import com.github.polyrocketmatt.delegate.core.command.trigger.FailureTrigger;
import com.github.polyrocketmatt.delegate.core.command.trigger.SuccessTrigger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class CommandBuilderImpl extends DelegateCommandBuilder {

    @Override
    public @NotNull CommandBuilderImpl with(@NotNull ICommandAttribute attribute) {
        if (attribute instanceof CommandAction && ((CommandAction) attribute).getPrecedence() < 0)
            throw new AttributeException("Action precedence must be greater than 0");
        this.attributes.add((CommandAttribute) attribute);
        return this;
    }

    @Override
    public @NotNull CommandBuilderImpl withAlias(@NotNull String alias) {
        return this.with(new AliasDefinition(alias));
    }

    @Override
    public @NotNull CommandBuilderImpl withAliases(String... aliases) {
        for (String alias : aliases)
            this.withAlias(alias);
        return this;
    }

    @Override
    public @NotNull CommandBuilderImpl withAction(@NotNull CommandAction action) {
        //  Check that action precedence is greater than or equal to 0
        if (action.getPrecedence() < 0)
            throw new AttributeException("Action precedence must be greater or equal 0");
        return this.with(action);
    }

    @Override
    public @NotNull CommandBuilderImpl withArgument(@NotNull CommandArgument<?> argument) {
        return this.with(argument);
    }

    @Override
    public @NotNull CommandBuilderImpl withDouble(@NotNull String name, @NotNull String description) {
        return this.withArgument(DoubleArgument.of(name, description));
    }

    @Override
    public @NotNull CommandBuilderImpl withDouble(@NotNull String name, @NotNull String description, double defaultValue) {
        return this.withArgument(DoubleArgument.of(name, description, defaultValue));
    }

    @Override
    public @NotNull CommandBuilderImpl withFloat(@NotNull String name, @NotNull String description) {
        return this.with(FloatArgument.of(name, description));
    }

    @Override
    public @NotNull CommandBuilderImpl withFloat(@NotNull String name, @NotNull String description, float defaultValue) {
        return this.with(FloatArgument.of(name, description, defaultValue));
    }

    @Override
    public @NotNull CommandBuilderImpl withInt(@NotNull String name, @NotNull String description) {
        return this.with(IntArgument.of(name, description));
    }

    @Override
    public @NotNull CommandBuilderImpl withInt(@NotNull String name, @NotNull String description, int defaultValue) {
        return this.with(IntArgument.of(name, description, defaultValue));
    }

    @Override
    public @NotNull CommandBuilderImpl withString(@NotNull String name, @NotNull String description) {
        return this.with(StringArgument.of(name, description));
    }

    @Override
    public @NotNull CommandBuilderImpl withString(@NotNull String name, @NotNull String description, @Nullable String defaultValue) {
        return this.with(StringArgument.of(name, description, defaultValue));
    }

    @Override
    public @NotNull CommandBuilderImpl withBool(@NotNull String name, @NotNull String description) {
        return this.with(BoolArgument.of(name, description));
    }

    @Override
    public @NotNull CommandBuilderImpl withBool(@NotNull String name, @NotNull String description, boolean defaultValue) {
        return this.with(BoolArgument.of(name, description, defaultValue));
    }

    @Override
    public @NotNull CommandBuilderImpl withLong(@NotNull String name, @NotNull String description) {
        return this.with(LongArgument.of(name, description));
    }

    @Override
    public @NotNull CommandBuilderImpl withLong(@NotNull String name, @NotNull String description, long defaultValue) {
        return this.with(LongArgument.of(name, description, defaultValue));
    }

    @Override
    public @NotNull CommandBuilderImpl withDefinition(@NotNull CommandDefinition<?> definition) {
        return this.with(definition);
    }

    @Override
    public @NotNull CommandBuilderImpl withSubcommand(@NotNull CommandDefinition<?> subcommand) {
        if (!(subcommand instanceof SubcommandDefinition))
            throw new AttributeException("Subcommand must be a SubcommandDefinition");
        return this.with(subcommand);
    }

    @Override
    public @NotNull CommandBuilderImpl withSubcommand(@NotNull String name, @NotNull String description) {
        return this.withSubcommand(new SubcommandDefinition(name, description));
    }

    @Override
    public @NotNull CommandBuilderImpl withSubcommand(@NotNull DelegateCommandBuilder builder) {
        List<NameDefinition> name = builder.getDefinitions()
                .stream()
                .filter(def -> def instanceof NameDefinition)
                .map(def -> (NameDefinition) def)
                .toList();
        List<DescriptionDefinition> description = builder.getDefinitions()
                .stream()
                .filter(def -> def instanceof DescriptionDefinition)
                .map(def -> (DescriptionDefinition) def)
                .toList();
        if (name.size() != 1)
            throw new AttributeException("Subcommand must have exactly one name");
        if (description.size() != 1)
            throw new AttributeException("Subcommand must have exactly one description");
        return this.with(new SubcommandDefinition(name.get(0).getValue(), description.get(0).getValue()));
    }

    @Override
    public @NotNull CommandBuilderImpl withProperty(@NotNull CommandProperty property) {
        return this.with(property);
    }

    @Override
    public @NotNull CommandBuilderImpl withAsync() {
        return this.with(new AsyncProperty());
    }

    @Override
    public @NotNull CommandBuilderImpl withIgnoreNull() {
        return this.with(new IgnoreNullProperty());
    }

    @Override
    public @NotNull CommandBuilderImpl withIgnoreNonPresent() {
        return this.with(new IgnoreNonPresentProperty());
    }

    @Override
    public @NotNull CommandBuilderImpl withExceptionCatching() {
        return this.with(new CatchExceptionProperty());
    }

    @Override
    public @NotNull CommandBuilderImpl withPermission(@NotNull PermissionTier tier) {
        return this.with(tier);
    }

    @Override
    public @NotNull CommandBuilderImpl withPermission(@NotNull String permission, @NotNull PermissionTier parent) {
        return this.with(new StandardPermission(permission, parent));
    }

    @Override
    public @NotNull CommandBuilderImpl withPermission(@NotNull String permission) {
        return this.withPermission(new StandardPermission(permission));
    }

    @Override
    public @NotNull CommandBuilderImpl withOperatorPermission() {
        return this.withPermission(PermissionTierType.OPERATOR.getTier());
    }

    @Override
    public @NotNull CommandBuilderImpl withGlobalPermission() {
        return this.withPermission(PermissionTierType.GLOBAL.getTier());
    }

    @Override
    public @NotNull CommandBuilderImpl withConsumerAction(@NotNull BiConsumer<CommanderEntity, Context> action) {
        return this.with(new ConsumerAction(action));
    }

    @Override
    public @NotNull CommandBuilderImpl withConsumerAction(@NotNull String identifier, @NotNull BiConsumer<CommanderEntity, Context> action) {
        return this.with(new ConsumerAction(identifier, action));
    }

    @Override
    public @NotNull CommandBuilderImpl withFunctionAction(@NotNull BiFunction<CommanderEntity, Context, ?> action) {
        return this.with(new FunctionAction(action));
    }

    @Override
    public @NotNull CommandBuilderImpl withFunctionAction(@NotNull String identifier, @NotNull BiFunction<CommanderEntity, Context, ?> action) {
        return this.with(new FunctionAction(identifier, action));
    }

    @Override
    public @NotNull CommandBuilderImpl withRunnableAction(@NotNull Runnable action) {
        return this.with(new RunnableAction(action));
    }

    @Override
    public @NotNull CommandBuilderImpl withRunnableAction(@NotNull String identifier, @NotNull Runnable action) {
        return this.with(new RunnableAction(identifier, action));
    }

    @Override
    public <T> @NotNull CommandBuilderImpl withSupplierAction(@NotNull Supplier<T> action) {
        return this.with(new SupplierAction<>(action));
    }

    @Override
    public <T> @NotNull CommandBuilderImpl withSupplierAction(@NotNull String identifier, @NotNull Supplier<T> action) {
        return this.with(new SupplierAction<>(identifier, action));
    }

    @Override
    public @NotNull CommandBuilderImpl onExcept(@NotNull TriConsumer<CommanderEntity, FeedbackType, List<String>> action) {
        return this.with(new ExceptAction(action));
    }

    @Override
    public @NotNull CommandBuilderImpl onSucces(@NotNull BiConsumer<CommandDispatchInformation, CommandCapture> onSuccess) {
        return this.with(new SuccessTrigger(onSuccess));
    }

    @Override
    public @NotNull CommandBuilderImpl onFail(@NotNull BiConsumer<CommandDispatchInformation, CommandCapture> onFail) {
        return this.with(new FailureTrigger(onFail));
    }
}
