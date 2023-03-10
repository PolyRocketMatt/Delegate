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

public class StandardPermissionTest {

    private final PlayerCommander commanderMock = new PlayerCommander(UUID.randomUUID()) {
        @Override
        public boolean hasPermission(@NotNull String permission) {
            return "test.permission".equals(permission) || "test.parent".equals(permission);
        }

        @Override
        public boolean isOperator() {
            return false;
        }

        @Override
        public void sendMessage(@NotNull String message) {

        }

        @Override
        public boolean isPlayer() {
            return true;
        }
    };

    @Test
    public void testPrimaryConstructor() {
        StandardPermission permission = new StandardPermission("test.permission");

        assertEquals(AttributeType.PERMISSION, permission.getType());
        assertEquals("test.permission", permission.getPermission());
        assertNull(permission.getParent());
    }

    @Test
    public void testSecondaryConstructor() {
        StandardPermission parent = new StandardPermission("test.parent");
        StandardPermission permission = new StandardPermission("test.permission", parent);

        assertEquals(AttributeType.PERMISSION, permission.getType());
        assertEquals("test.permission", permission.getPermission());
        assertEquals(parent, permission.getParent());
    }

    @Test
    public void testPrimaryConstructorNull() {
        assertThrows(IllegalArgumentException.class, () -> new StandardPermission(null));
    }

    @Test
    public void testHasPermissionNull() {
        StandardPermission permission = new StandardPermission("test.permission");

        assertThrows(IllegalArgumentException.class, () -> permission.hasPermission(null));
    }

    @Test
    public void testHasPermissionConsoleCommanderParentNull() {
        StandardPermission permission = new StandardPermission("test.permission");

        assertTrue(permission.hasPermission(new ConsoleCommander()));
    }

    @Test
    public void testHasPermissionConsoleCommanderParentOperator() {
        OperatorPermission parent = new OperatorPermission();
        StandardPermission permission = new StandardPermission("test.permission", parent);

        assertTrue(permission.hasPermission(new ConsoleCommander()));
    }

    @Test
    public void testHasPermissionConsoleCommanderParentGlobal() {
        StandardPermission parent = new StandardPermission("test.parent");
        StandardPermission permission = new StandardPermission("test.permission", parent);

        assertTrue(permission.hasPermission(new ConsoleCommander()));
    }

    @Test
    public void testHasPermissionConsoleCommanderParentStandard() {
        StandardPermission parent = new StandardPermission("test.parent");
        StandardPermission permission = new StandardPermission("test.permission", parent);

        assertTrue(permission.hasPermission(new ConsoleCommander()));
    }

    @Test
    public void testHasPermissionEntityCommanderParentNull() {
        StandardPermission permission = new StandardPermission("test.permission");

        assertTrue(permission.hasPermission(commanderMock));
    }

    @Test
    public void testHasPermissionEntityCommanderParentOperator() {
        OperatorPermission parent = new OperatorPermission();
        StandardPermission permission = new StandardPermission("test.permission", parent);

        assertFalse(permission.hasPermission(commanderMock));
    }

    @Test
    public void testHasPermissionEntityCommanderParentGlobal() {
        GlobalPermission parent = new GlobalPermission();
        StandardPermission permission = new StandardPermission("test.permission", parent);

        assertTrue(permission.hasPermission(commanderMock));
    }

    @Test
    public void testHasPermissionEntityCommanderParentStandardValid() {
        StandardPermission parent = new StandardPermission("test.parent");
        StandardPermission permission = new StandardPermission("test.permission", parent);

        assertTrue(permission.hasPermission(commanderMock));
    }

    @Test
    public void testHasPermissionEntityCommanderParentStandardInvalid() {
        StandardPermission parent = new StandardPermission("test.invalid_parent");
        StandardPermission permission = new StandardPermission("test.permission", parent);

        assertFalse(permission.hasPermission(commanderMock));
    }

    @Test
    public void testHasPermissionEntityCommanderParentStandardDoubleInvalid() {
        StandardPermission parent = new StandardPermission("test.invalid_parent");
        StandardPermission permission = new StandardPermission("test.invalid_permission", parent);

        assertFalse(permission.hasPermission(commanderMock));
    }

}
