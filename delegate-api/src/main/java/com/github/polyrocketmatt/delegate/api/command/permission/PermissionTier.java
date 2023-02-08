// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.permission;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.Bufferable;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;

import java.util.UUID;

public abstract class PermissionTier extends CommandAttribute implements Bufferable {

    public PermissionTier() {
        super(UUID.randomUUID().toString());
    }

    public abstract boolean hasPermission(CommanderEntity entity);

    @Override
    public AttributeType getType() {
        return AttributeType.PERMISSION;
    }
}
