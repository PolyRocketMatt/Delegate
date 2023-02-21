// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.permission;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.Bufferable;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class PermissionTier extends CommandAttribute implements Bufferable {

    public PermissionTier(@NotNull String identifier) {
        super(identifier);
    }

    public PermissionTier() {
        this(UUID.randomUUID().toString());
    }

    public abstract boolean hasPermission(CommanderEntity entity);

    @Override
    public @NotNull AttributeType getType() {
        return AttributeType.PERMISSION;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PermissionTier;
    }
}
