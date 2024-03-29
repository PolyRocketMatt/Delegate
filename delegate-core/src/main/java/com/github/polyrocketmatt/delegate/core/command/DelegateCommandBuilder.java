// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.ICommandBuilder;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;
import com.github.polyrocketmatt.delegate.api.command.trigger.CommandTrigger;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.core.command.action.ExceptAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;
import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;

/**
 * Represents an extendable chain of attributes that can be applied to a command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public abstract class DelegateCommandBuilder implements ICommandBuilder {

    protected final LinkedList<CommandAttribute> attributes;

    protected DelegateCommandBuilder() {
        this.attributes = new LinkedList<>();
    }

    /**
     * Uses the {@link com.github.polyrocketmatt.delegate.core.handlers.AttributeHandler} to
     * process the attributes in the chain and constructs a new {@link DelegateCommand} instance.
     */
    @Override
    public @NotNull DelegateCommand build() {
        return getDelegate().getAttributeHandler().process(null, new AttributedDelegateCommand(this), true);
    }

    /**
     * Filter the chain for attributes of the specified type.
     *
     * @param instance The type to filter for.
     * @return A list of attributes of the specified type.
     * @param <T> The type of attribute to filter for.
     */
    public <T extends CommandAttribute> @NotNull List<CommandAttribute> filter(@NotNull Class<T> instance) {
        validate("instance", Class.class, instance);

        List<CommandAttribute> filtered = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (instance.isInstance(attribute))
                filtered.add(attribute);
        return filtered;
    }

    /**
     * Filter the chain for attributes that fulfill the specified rule.
     *
     * @param rule The rule to filter for.
     * @return A list of attributes that fulfill the specified rule.
     */
    public @NotNull List<CommandAttribute> filter(@NotNull Function<CommandAttribute, Boolean> rule) {
        validate("rule", Function.class, rule);

        List<CommandAttribute> filtered = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (rule.apply(attribute))
                filtered.add(attribute);
        return filtered;
    }

    /**
     * Gets all {@link CommandAction}s in the chain.
     *
     * @return A list of all actions in the chain.
     */
    public @NotNull List<CommandAction> getActions() {
        List<CommandAction> actions = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof CommandAction)
                actions.add((CommandAction) attribute);
        return actions;
    }

    /**
     * Gets all {@link CommandArgument}s in the chain.
     *
     * @return A list of all arguments in the chain.
     */
    public @NotNull List<CommandArgument<?>> getArguments() {
        List<CommandArgument<?>> arguments = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof CommandArgument<?>)
                arguments.add((CommandArgument<?>) attribute);
        return arguments;
    }

    /**
     * Gets all {@link CommandDefinition}s in the chain.
     *
     * @return A list of all definitions in the chain.
     */
    public @NotNull List<CommandDefinition<?>> getDefinitions() {
        List<CommandDefinition<?>> definitions = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof CommandDefinition<?>)
                definitions.add((CommandDefinition<?>) attribute);
        return definitions;
    }

    /**
     * Gets all {@link CommandProperty}s in the chain.
     *
     * @return A list of all properties in the chain.
     */
    public @NotNull List<CommandProperty> getProperties() {
        List<CommandProperty> properties = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof CommandProperty)
                properties.add((CommandProperty) attribute);
        return properties;
    }

    public @NotNull List<CommandTrigger> getTriggers() {
        List<CommandTrigger> triggers = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof CommandTrigger)
                triggers.add((CommandTrigger) attribute);
        return triggers;
    }

    public @NotNull List<PermissionTier> getPermissionTiers() {
        List<PermissionTier> tiers = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof PermissionTier)
                tiers.add((PermissionTier) attribute);
        return tiers;
    }

    public @NotNull List<ExceptAction> getExceptActions() {
        List<ExceptAction> actions = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof ExceptAction)
                actions.add((ExceptAction) attribute);
        return actions;
    }

    /**
     * Find the first {@link CommandAttribute} in the chain that is an instance of the specified type.
     *
     * @param instance The type to find.
     * @return The first attribute of the specified type.
     * @param <T> The type of attribute to find.
     */
    public <T extends CommandAttribute> @Nullable CommandAttribute find(Class<T> instance) {
        for (CommandAttribute attribute : attributes)
            if (instance.isInstance(attribute))
                return attribute;
        return null;
    }

    /**
     * Gets the size of the chain.
     *
     * @return The size of the chain.
     */
    public int size() {
        return attributes.size();
    }

}
