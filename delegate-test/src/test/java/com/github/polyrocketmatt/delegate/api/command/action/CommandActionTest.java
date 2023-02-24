package com.github.polyrocketmatt.delegate.api.command.action;// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandActionTest {

    private static class MockAction extends CommandAction {

        public MockAction(@NotNull String identifier, @NotNull PermissionTier permissionTier, int precedence) {
            super(identifier, permissionTier, precedence);
        }

        @Override
        public @NotNull PermissionTier getPermissionTier() {
            return super.getPermissionTier();
        }

        @Override
        public int getPrecedence() {
            return super.getPrecedence();
        }

        @Override
        public @NotNull AttributeType getType() {
            return super.getType();
        }

        @Override
        public @NotNull ActionItem<?> run(@NotNull CommanderEntity commander, @NotNull List<Argument<?>> arguments) {
            return new ActionItem<>(ActionItem.Result.SUCCESS, "Mock action ran successfully");
        }
    }

    private static class MockPermission extends PermissionTier {

        public MockPermission() {
            super();
        }

        @Override
        public boolean hasPermission(CommanderEntity entity) {
            return false;
        }

        @Override
        public @NotNull AttributeType getType() {
            return super.getType();
        }
    }

    private final MockPermission permission = new MockPermission();

    @Test
    public void testConstructor() {
        MockAction action = new MockAction("test", permission, 0);

        assertEquals("test", action.getIdentifier());
        assertEquals(permission, action.getPermissionTier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
    }

    @Test
    public void testConstructorIllegalPermission() {
        assertThrows(IllegalArgumentException.class, () -> new MockAction("test", null, 0));
    }

    @Test
    public void testConstructorIllegalPrecedence() {
        assertThrows(IllegalArgumentException.class, () -> new MockAction("test", permission, -1));
    }

}
