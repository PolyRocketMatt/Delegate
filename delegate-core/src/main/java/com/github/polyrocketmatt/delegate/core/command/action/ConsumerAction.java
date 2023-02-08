// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.Context;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.FailureActionResult;
import com.github.polyrocketmatt.delegate.api.command.data.SuccessActionResult;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTiers;

import java.util.List;
import java.util.function.BiConsumer;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

/**
 * A {@link BiConsumer}-based command action that does not yield a result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class ConsumerAction extends CommandAction {

    private final BiConsumer<CommanderEntity, Context> action;

    /**
     * Creates a new {@link ConsumerAction} with an identifier and {@link BiConsumer} and
     * a default precedence of 0.
     *
     * @param action The consumer that will be executed.
     * @throws IllegalArgumentException If the action is null.
     */
    public ConsumerAction(BiConsumer<CommanderEntity, Context> action) throws IllegalArgumentException {
        this(newId(), PermissionTiers.GLOBAL.getTier(), 0, action);
    }

    /**
     * Creates a new {@link ConsumerAction} with an identifier and {@link BiConsumer} and
     * a default precedence of 0.
     *
     * @param permissionTier The permission tier required to execute this action.
     * @param action The consumer that will be executed.
    * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public ConsumerAction(PermissionTier permissionTier, BiConsumer<CommanderEntity, Context> action) throws IllegalArgumentException {
        this(newId(), permissionTier, 0, action);
    }

    /**
     * Creates a new {@link ConsumerAction} with an identifier, precedence and {@link BiConsumer}.
     *
     * @param precedence The precedence of the command action.
     * @param action The consumer that will be executed.
     * @throws IllegalArgumentException If the action is null.
     */
    public ConsumerAction(int precedence, BiConsumer<CommanderEntity, Context> action) throws IllegalArgumentException {
        this(newId(), PermissionTiers.GLOBAL.getTier(), precedence, action);
    }

    /**
     * Creates a new {@link ConsumerAction} with an identifier, precedence and {@link BiConsumer}.
     *
     * @param permissionTier The permission tier required to execute the command action.
     * @param precedence The precedence of the command action.
     * @param action The consumer that will be executed.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public ConsumerAction(PermissionTier permissionTier, int precedence, BiConsumer<CommanderEntity, Context> action)
            throws IllegalArgumentException {
        this(newId(), permissionTier, precedence, action);
    }

    /**
     * Creates a new {@link ConsumerAction} with an identifier, precedence and {@link BiConsumer}.
     *
     * @param identifier The identifier of the command action.
     * @param action The consumer that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the action is null.
     */
    public ConsumerAction(String identifier, BiConsumer<CommanderEntity, Context> action) throws IllegalArgumentException {
        this(identifier, PermissionTiers.GLOBAL.getTier(), 0, action);
    }

    /**
     * Creates a new {@link ConsumerAction} with an identifier, precedence and {@link BiConsumer}.
     *
     * @param identifier The identifier of the command action.
     * @param permissionTier The permission tier required to execute the command action.
     * @param action The consumer that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public ConsumerAction(String identifier, PermissionTier permissionTier, BiConsumer<CommanderEntity, Context> action)
            throws IllegalArgumentException {
        this(identifier, permissionTier, 0, action);
    }

    /**
     * Creates a new {@link ConsumerAction} with an identifier, precedence and {@link BiConsumer}.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     * @param action The consumer that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the action is null.
     */
    public ConsumerAction(String identifier, int precedence, BiConsumer<CommanderEntity, Context> action)
            throws IllegalArgumentException {
        this(identifier, PermissionTiers.GLOBAL.getTier(), precedence, action);
    }

    /**
     * Creates a new {@link ConsumerAction} with an identifier, precedence and {@link BiConsumer}.
     *
     * @param identifier The identifier of the command action.
     * @param permissionTier The permission tier required to execute the command action.
     * @param precedence The precedence of the command action.
     * @param action The consumer that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public ConsumerAction(String identifier, PermissionTier permissionTier, int precedence, BiConsumer<CommanderEntity, Context> action)
            throws IllegalArgumentException {
        super(identifier, permissionTier, precedence);
        if (action == null)
            throw new IllegalArgumentException("Action cannot be null");
        this.action = action;
    }

    @Override
    public ActionItem<?> run(CommanderEntity commander, List<Argument<?>> arguments) {
        try {
            action.accept(commander, new Context(arguments));
        } catch (Exception ex) {
            ex.printStackTrace();

            return new FailureActionResult(ex);
        }

        return new SuccessActionResult();
    }

}
