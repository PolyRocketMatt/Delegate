package com.github.polyrocketmatt.delegate.core.command.permission;

import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;

public class StandardPermission extends PermissionTier {

    private final String permission;
    private final PermissionTier parent;

    public StandardPermission(String permission) {
        this.permission = permission;
        this.parent = null;
    }

    public StandardPermission(String permission, PermissionTier parent) {
        this.permission = permission;
        this.parent = parent;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public boolean hasPermission(CommanderEntity entity) {
        //  If the parent permission is not null and the entity has the parent permission
        //  then we can assume that the entity has this permission.
        if (parent != null) {
            if (parent instanceof OperatorPermission)
                return entity.isOperator();
            else if (parent instanceof GlobalPermission)
                return true;
            else if (parent instanceof StandardPermission)
                return entity.hasPermission(((StandardPermission) parent).getPermission());
            else
                return false;
        } else
            return entity.hasPermission(permission);
    }
}
