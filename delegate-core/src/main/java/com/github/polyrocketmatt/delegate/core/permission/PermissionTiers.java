package com.github.polyrocketmatt.delegate.core.permission;

import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;

public enum PermissionTiers {

    OPERATOR(new OperatorPermission()),
    GLOBAL(new GlobalPermission());

    private final PermissionTier tier;

    PermissionTiers(PermissionTier tier) {
        this.tier = tier;
    }

    public PermissionTier getTier() {
        return tier;
    }

}
