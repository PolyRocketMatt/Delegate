// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.entity;

import org.jetbrains.annotations.NotNull;

/**
 * Defines an entity that can issue commands.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public interface CommanderEntity {

    boolean hasPermission(@NotNull String permission);

    boolean isOperator();

    void sendMessage(@NotNull String message);

}
