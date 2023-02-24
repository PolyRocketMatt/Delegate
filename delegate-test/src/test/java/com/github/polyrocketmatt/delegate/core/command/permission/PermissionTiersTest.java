// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.permission;

import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PermissionTiersTest {

    private final PermissionTier[] builtInTypes = new PermissionTier[] { PermissionTierType.OPERATOR.getTier(), PermissionTierType.GLOBAL.getTier() };

    @Test
    public void testBuiltInTiers() {
        for (PermissionTierType type : PermissionTierType.values())
            assertTrue(Arrays.stream(builtInTypes).anyMatch(tier -> tier.equals(type.getTier())));
    }

}
