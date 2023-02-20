// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.permission;

import com.github.polyrocketmatt.delegate.api.entity.ConsoleCommander;
import com.github.polyrocketmatt.delegate.api.entity.PlayerCommander;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OperatorPermissionTest {

    @Test
    public void testHasPermission() {
        OperatorPermission permission = new OperatorPermission();

        assertThrows(IllegalArgumentException.class, () -> permission.hasPermission(null));
        assertTrue(permission.hasPermission(new ConsoleCommander()));
        assertTrue(permission.hasPermission(new PlayerCommander(UUID.randomUUID()) {
            @Override
            public boolean hasPermission(String permission) {
                return true;
            }

            @Override
            public boolean isOperator() {
                return true;
            }

            @Override
            public void sendMessage(String message) {

            }
        }));

        assertFalse(permission.hasPermission(new PlayerCommander(UUID.randomUUID()) {
            @Override
            public boolean hasPermission(String permission) {
                return false;
            }

            @Override
            public boolean isOperator() {
                return false;
            }

            @Override
            public void sendMessage(String message) {

            }
        }));
    }

}
