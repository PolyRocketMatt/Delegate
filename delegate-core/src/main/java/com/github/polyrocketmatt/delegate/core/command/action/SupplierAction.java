// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.FailureActionResult;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTiers;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

/**
 * A {@link Supplier}-based command action that does not take any arguments and only yields a result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class SupplierAction<T> extends CommandAction {

    private final Supplier<T> action;

    /**
     * Creates a new {@link SupplierAction} with an identifier and {@link Supplier} and
     * a default precedence of 0.
     *
     * @param action The supplier that will be executed.
     */
    public SupplierAction(Supplier<T> action) {
        super(newId(), PermissionTiers.GLOBAL.getTier(), 0);
        this.action = action;
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier and {@link Supplier} and
     * a default precedence of 0.
     *
     * @param permissionTier The permission tier required to execute this action.
     * @param action The supplier that will be executed.
     */
    public SupplierAction(PermissionTier permissionTier, Supplier<T> action) {
        super(newId(), permissionTier, 0);
        this.action = action;
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param precedence The precedence of the command action.
     * @param action The supplier that will be executed.
     */
    public SupplierAction(int precedence, Supplier<T> action) {
        super(newId(), PermissionTiers.GLOBAL.getTier(), precedence);
        this.action = action;
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param permissionTier The permission tier required to execute this command.
     * @param precedence The precedence of the command action.
     * @param action The supplier that will be executed.
     */
    public SupplierAction(PermissionTier permissionTier, int precedence, Supplier<T> action) {
        super(newId(), permissionTier, precedence);
        this.action = action;
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param identifier The identifier of the command action.
     * @param action The supplier that will be executed.
     */
    public SupplierAction(String identifier, Supplier<T> action) {
        super(identifier, PermissionTiers.GLOBAL.getTier(), 0);
        this.action = action;
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param identifier The identifier of the command action.
     * @param permissionTier The permission tier required to execute this command.
     * @param action The supplier that will be executed.
     */
    public SupplierAction(String identifier, PermissionTier permissionTier, Supplier<T> action) {
        super(identifier, permissionTier, 0);
        this.action = action;
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     * @param action The {@link Consumer} that will be executed.
     */
    public SupplierAction(String identifier, int precedence, Supplier<T> action) {
        super(identifier, PermissionTiers.GLOBAL.getTier(), precedence);
        this.action = action;
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param identifier The identifier of the command action.
     * @param permissionTier The permission tier required to execute this command.
     * @param precedence The precedence of the command action.
     * @param action The {@link Consumer} that will be executed.
     */
    public SupplierAction(String identifier, PermissionTier permissionTier, int precedence, Supplier<T> action) {
        super(identifier, permissionTier, precedence);
        this.action = action;
    }

    @Override
    public ActionItem<?> run(CommanderEntity commander, List<Argument<?>> arguments) {
        try {
            return new ActionItem<>(ActionItem.Result.SUCCESS, action.get());
        } catch (Exception ex) {
            return new FailureActionResult(ex);
        }
    }

}
