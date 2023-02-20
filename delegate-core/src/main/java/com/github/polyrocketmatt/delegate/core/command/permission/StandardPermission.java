// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.permission;

import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;

public class StandardPermission extends PermissionTier {

    private final String permission;
    private final PermissionTier parent;

    public StandardPermission(String permission, PermissionTier parent) {
        if (permission == null)
            throw new IllegalArgumentException("Permission cannot be null");
        this.permission = permission;
        this.parent = parent;
    }

    public StandardPermission(String permission) {
        this(permission, null);
    }

    public String getPermission() {
        return permission;
    }

    public PermissionTier getParent() {
        return parent;
    }

    @Override
    public boolean hasPermission(CommanderEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("CommanderEntity cannot be null");

        //  If the parent permission is not null and the entity has the parent permission
        //  then we can assume that the entity has this permission.
        if (parent != null) {
            if (parent instanceof OperatorPermission)
                return entity.isOperator();
            else if (parent instanceof GlobalPermission)
                return true;
            else
                return entity.hasPermission(((StandardPermission) parent).getPermission()) && entity.hasPermission(permission);
        } else
            return entity.hasPermission(permission);
    }
}
