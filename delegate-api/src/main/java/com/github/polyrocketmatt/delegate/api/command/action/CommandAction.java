// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.action;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.Bufferable;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import org.jetbrains.annotations.NotNull;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Defines a command action with a precedence.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public abstract class CommandAction extends CommandAttribute implements Bufferable, RunnableCommandAction {

    private final PermissionTier permissionTier;
    private final int precedence;

    /**
     * Creates a new command action with an identifier and a precedence.
     *
     * @param identifier The identifier of the command action.
     * @param permissionTier The permission tier of the command action.
     * @param precedence The precedence of the command action.
     */
    public CommandAction(@NotNull String identifier, @NotNull PermissionTier permissionTier, int precedence) {
        super(identifier);
        validate("permissionTier", PermissionTier.class, permissionTier);

        if (precedence < 0)
            throw new IllegalArgumentException("Precedence cannot be negative");
        this.permissionTier = permissionTier;
        this.precedence = precedence;
    }

    /**
     * Gets the {@link PermissionTier} of the command action.
     *
     * @return The permission tier of the command action.
     */
    public @NotNull PermissionTier getPermissionTier() {
        return permissionTier;
    }

    /**
     * Gets the precedence of the command action.
     *
     * @return The precedence of the command action.
     */
    public int getPrecedence() {
        return precedence;
    }

    @Override
    public @NotNull AttributeType getType() {
        return AttributeType.ACTION;
    }
}
