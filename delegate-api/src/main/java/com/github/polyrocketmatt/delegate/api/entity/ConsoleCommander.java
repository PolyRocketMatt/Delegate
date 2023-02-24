// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.entity;

import org.jetbrains.annotations.NotNull;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Represents a command issuer from the console.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class ConsoleCommander implements CommanderEntity {

    @Override
    public boolean hasPermission(@NotNull String permission) {
        validate("permission", String.class, permission);

        return true;
    }

    @Override
    public boolean isOperator() {
        return true;
    }

    @Override
    public void sendMessage(@NotNull String message) {
        validate("message", String.class, message);

        System.out.println(message);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        return object instanceof ConsoleCommander;
    }

}
