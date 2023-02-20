// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.Context;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.FailureActionResult;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTierType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiFunction;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;
import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;

/**
 * A {@link BiFunction}-based command action that takes arguments and yields a result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class FunctionAction extends CommandAction {

    private final BiFunction<CommanderEntity, Context, ?> action;

    /**
     * Creates a new {@link FunctionAction} with an identifier and {@link BiFunction} and
     * a default precedence of 0.
     *
     * @param action The function that will be executed.
     * @throws IllegalArgumentException If the action is null.
     */
    public FunctionAction(BiFunction<CommanderEntity, Context, ?> action) {
        this(newId(), PermissionTierType.GLOBAL.getTier(), 0, action);
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier and {@link BiFunction} and
     * a default precedence of 0.
     *
     * @param permissionTier The permission tier required to execute this action.
     * @param action The function that will be executed.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public FunctionAction(PermissionTier permissionTier, BiFunction<CommanderEntity, Context, ?> action) {
        this(newId(), permissionTier, 0, action);
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link BiFunction}.
     *
     * @param precedence The precedence of the command action.
     * @param action The function that will be executed.
     * @throws IllegalArgumentException If the action is null.
     */
    public FunctionAction(int precedence, BiFunction<CommanderEntity, Context, ?> action) {
        this(newId(), PermissionTierType.GLOBAL.getTier(), precedence, action);
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link BiFunction}.
     *
     * @param permissionTier The permission tier required to execute this action.
     * @param precedence The precedence of the command action.
     * @param action The function that will be executed.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public FunctionAction(PermissionTier permissionTier, int precedence, BiFunction<CommanderEntity, Context, ?> action) {
        this(newId(), permissionTier, precedence, action);
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link BiFunction}.
     *
     * @param identifier The identifier of the command action.
     * @param action The function that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the action is null.
     */
    public FunctionAction(String identifier, BiFunction<CommanderEntity, Context, ?> action) {
        this(identifier, PermissionTierType.GLOBAL.getTier(), 0, action);
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link BiFunction}.
     *
     * @param identifier The identifier of the command action.
     * @param permissionTier The permission tier required to execute this action.
     * @param action The function that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public FunctionAction(String identifier, PermissionTier permissionTier, BiFunction<CommanderEntity, Context, ?> action) {
        this(identifier, permissionTier, 0, action);
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link BiFunction}.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     * @param action The function that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the action is null.
     */
    public FunctionAction(String identifier, int precedence, BiFunction<CommanderEntity, Context, ?> action) {
        this(identifier, PermissionTierType.GLOBAL.getTier(), precedence, action);
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link BiFunction}.
     *
     * @param identifier The identifier of the command action.
     * @param permissionTier The permission tier required to execute this action.
     * @param precedence The precedence of the command action.
     * @param action The function that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public FunctionAction(@NotNull String identifier, @NotNull PermissionTier permissionTier, int precedence,
                          @NotNull BiFunction<CommanderEntity, @NotNull Context, ?> action) {
        super(identifier, permissionTier, precedence);
        this.action = action;
    }

    @Override
    public ActionItem<?> run(CommanderEntity commander, List<Argument<?>> arguments) {
        if (commander == null)
            throw new IllegalArgumentException("Commander cannot be null");
        if (arguments == null)
            throw new IllegalArgumentException("Arguments cannot be null");

        try {
            return new ActionItem<>(ActionItem.Result.SUCCESS, action.apply(commander, new Context(arguments)));
        } catch (Exception ex) {
            if (getDelegate().isVerbose())
                ex.printStackTrace();
            return new FailureActionResult(ex);
        }
    }

}
