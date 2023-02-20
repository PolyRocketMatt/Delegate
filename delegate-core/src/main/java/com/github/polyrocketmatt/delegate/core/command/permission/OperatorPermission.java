// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.permission;

import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;

public class OperatorPermission extends PermissionTier {

    @Override
    public boolean hasPermission(CommanderEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("CommanderEntity cannot be null");
        return entity.isOperator();
    }
}
