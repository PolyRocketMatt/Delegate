package com.github.polyrocketmatt.delegate.api.permission;

import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;

public abstract class PermissionTier {

    public abstract boolean hasPermission(CommanderEntity entity);

}
