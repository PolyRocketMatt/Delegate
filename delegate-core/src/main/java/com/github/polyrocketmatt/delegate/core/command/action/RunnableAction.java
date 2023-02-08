// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.FailureActionResult;
import com.github.polyrocketmatt.delegate.api.command.data.SuccessActionResult;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTiers;

import java.util.List;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

/**
 * A {@link Runnable}-based command action that does not yield a result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class RunnableAction extends CommandAction {

    private final Runnable action;

    /**
     * Creates a new {@link RunnableAction} with an identifier, precedence and {@link Runnable}.
     *
     * @param precedence The precedence of the command action.
     * @param action The runnable that will be executed.
     * @throws IllegalArgumentException If the action is null.
     */
    public RunnableAction(int precedence, Runnable action) {
        this(newId(), PermissionTiers.GLOBAL.getTier(), precedence, action);
    }

    /**
     * Creates a new {@link RunnableAction} with an identifier, precedence and {@link Runnable}.
     *
     * @param permissionTier The permission tier required to execute this action.
     * @param precedence The precedence of the command action.
     * @param action The runnable that will be executed.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public RunnableAction(PermissionTier permissionTier, int precedence, Runnable action) {
        this(newId(), permissionTier, precedence, action);
    }

    /**
     * Creates a new {@link RunnableAction} with an identifier and {@link Runnable} and
     * a default precedence of 0.
     *
     * @param action The runnable that will be executed.
     * @throws IllegalArgumentException If the action is null.
     */
    public RunnableAction(Runnable action) {
        this(newId(), PermissionTiers.GLOBAL.getTier(), 0, action);
    }

    /**
     * Creates a new {@link RunnableAction} with an identifier and {@link Runnable} and
     * a default precedence of 0.
     *
     * @param permissionTier The permission tier required to execute this action.
     * @param action The runnable that will be executed.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public RunnableAction(PermissionTier permissionTier, Runnable action) {
        this(permissionTier, 0, action);
    }

    /**
     * Creates a new {@link RunnableAction} with an identifier and {@link Runnable} and
     * a default precedence of 0.
     *
     * @param identifier The identifier of the command action.
     * @param action The runnable that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the action is null.
     */
    public RunnableAction(String identifier, Runnable action) {
        this(identifier, PermissionTiers.GLOBAL.getTier(), 0, action);
    }

    /**
     * Creates a new {@link RunnableAction} with an identifier and {@link Runnable} and
     * a default precedence of 0.
     *
     * @param identifier The identifier of the command action.
     * @param permissionTier The permission tier required to execute this action.
     * @param action The runnable that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public RunnableAction(String identifier, PermissionTier permissionTier, Runnable action) {
        this(identifier, permissionTier, 0, action);
    }

    /**
     * Creates a new {@link RunnableAction} with an identifier, precedence and {@link Runnable}.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     * @param action The runnable that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the action is null.
     */
    public RunnableAction(String identifier, int precedence, Runnable action) {
        this(identifier, PermissionTiers.GLOBAL.getTier(), precedence, action);
    }

    /**
     * Creates a new {@link RunnableAction} with an identifier, precedence and {@link Runnable}.
     *
     * @param identifier The identifier of the command action.
     * @param permissionTier The permission tier required to execute this action.
     * @param precedence The precedence of the command action.
     * @param action The runnable that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public RunnableAction(String identifier, PermissionTier permissionTier, int precedence, Runnable action) {
        super(identifier, permissionTier, precedence);
        if (action == null)
            throw new IllegalArgumentException("Action cannot be null");
        this.action = action;
    }

    @Override
    public ActionItem<?> run(CommanderEntity commander, List<Argument<?>> arguments) {
        try {
            action.run();
        } catch (Exception ex) {
            return new FailureActionResult(ex);
        }

        return new SuccessActionResult();
    }

}
