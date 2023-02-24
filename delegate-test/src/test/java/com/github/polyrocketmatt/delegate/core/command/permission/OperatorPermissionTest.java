// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.permission;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.entity.ConsoleCommander;
import com.github.polyrocketmatt.delegate.api.entity.PlayerCommander;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OperatorPermissionTest {

    @Test
    public void testHasPermission() {
        OperatorPermission permission = new OperatorPermission();

        assertEquals(AttributeType.PERMISSION, permission.getType());
        assertThrows(IllegalArgumentException.class, () -> permission.hasPermission(null));
        assertTrue(permission.hasPermission(new ConsoleCommander()));
        assertTrue(permission.hasPermission(new PlayerCommander(UUID.randomUUID()) {
            @Override
            public boolean hasPermission(@NotNull String permission) {
                return true;
            }

            @Override
            public boolean isOperator() {
                return true;
            }

            @Override
            public void sendMessage(@NotNull String message) {

            }
        }));

        assertFalse(permission.hasPermission(new PlayerCommander(UUID.randomUUID()) {
            @Override
            public boolean hasPermission(@NotNull String permission) {
                return false;
            }

            @Override
            public boolean isOperator() {
                return false;
            }

            @Override
            public void sendMessage(@NotNull String message) {

            }
        }));
    }

}
