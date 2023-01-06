package com.github.polyrocketmatt.delegate.core.permission;

import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.permission.PermissionTier;

public class OperatorPermission extends PermissionTier {

    @Override
    public boolean hasPermission(CommanderEntity entity) {
        return entity.isOperator();
    }
}
