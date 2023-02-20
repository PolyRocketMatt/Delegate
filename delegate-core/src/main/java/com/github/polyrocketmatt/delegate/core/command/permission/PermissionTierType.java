// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.permission;

import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;

public enum PermissionTierType {

    OPERATOR(new OperatorPermission()),
    GLOBAL(new GlobalPermission());

    private final PermissionTier tier;

    PermissionTierType(PermissionTier tier) {
        this.tier = tier;
    }

    public PermissionTier getTier() {
        return tier;
    }

}
