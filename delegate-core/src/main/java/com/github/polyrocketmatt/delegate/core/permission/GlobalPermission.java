package com.github.polyrocketmatt.delegate.core.permission;

import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;

public class GlobalPermission extends PermissionTier {

    @Override
    public boolean hasPermission(CommanderEntity entity) {
        return true;
    }
}
