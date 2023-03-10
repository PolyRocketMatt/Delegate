// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.FailureActionResult;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTierType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;
import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;
import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;

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
     * @throws IllegalArgumentException If the action is null.
     */
    public SupplierAction(Supplier<T> action) {
        this(newId(), PermissionTierType.GLOBAL.getTier(), 0, action);
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier and {@link Supplier} and
     * a default precedence of 0.
     *
     * @param permissionTier The permission tier required to execute this action.
     * @param action The supplier that will be executed.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public SupplierAction(PermissionTier permissionTier, Supplier<T> action) {
        this(newId(), permissionTier, 0, action);
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param precedence The precedence of the command action.
     * @param action The supplier that will be executed.
     * @throws IllegalArgumentException If the action is null.
     */
    public SupplierAction(int precedence, Supplier<T> action) {
        this(newId(), PermissionTierType.GLOBAL.getTier(), precedence, action);
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param permissionTier The permission tier required to execute this command.
     * @param precedence The precedence of the command action.
     * @param action The supplier that will be executed.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public SupplierAction(PermissionTier permissionTier, int precedence, Supplier<T> action) {
        this(newId(), permissionTier, precedence, action);
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param identifier The identifier of the command action.
     * @param action The supplier that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the action is null.
     */
    public SupplierAction(String identifier, Supplier<T> action) {
        this(identifier, PermissionTierType.GLOBAL.getTier(), 0, action);
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param identifier The identifier of the command action.
     * @param permissionTier The permission tier required to execute this command.
     * @param action The supplier that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public SupplierAction(String identifier, PermissionTier permissionTier, Supplier<T> action) {
        this(identifier, permissionTier, 0, action);
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     * @param action The supplier that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the action is null.
     */
    public SupplierAction(String identifier, int precedence, Supplier<T> action) {
        this(identifier, PermissionTierType.GLOBAL.getTier(), precedence, action);
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param identifier The identifier of the command action.
     * @param permissionTier The permission tier required to execute this command.
     * @param precedence The precedence of the command action.
     * @param action The supplier that will be executed.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the permission tier is null.
     * @throws IllegalArgumentException If the action is null.
     */
    public SupplierAction(@NotNull String identifier, @NotNull PermissionTier permissionTier, int precedence,
                          @NotNull Supplier<T> action) {
        super(identifier, permissionTier, precedence);
        this.action = action;
    }

    @Override
    public @NotNull ActionItem<?> run(@NotNull CommanderEntity commander, @NotNull List<Argument<?>> arguments) {
        validate("commander", CommanderEntity.class, commander);
        validate("arguments", List.class, arguments);
        for (Argument<?> argument : arguments)
            validate("argument", Argument.class, argument);

        try {
            return new ActionItem<>(ActionItem.Result.SUCCESS, action.get());
        } catch (Exception ex) {
            if (getDelegate().isVerbose())
                ex.printStackTrace();
            return new FailureActionResult(ex);
        }
    }

}
