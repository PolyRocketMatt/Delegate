// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTierType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class FunctionActionTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    private static class FunctionCommanderEntity implements CommanderEntity {

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
            System.out.println(message);
        }

        @Override
        public boolean isPlayer() {
            return false;
        }

    }

    @Test
    public void testPrimaryConstructor() {
        FunctionAction action = new FunctionAction((sender, context) -> null);

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSecondaryConstructor() {
        FunctionAction action = new FunctionAction(PermissionTierType.OPERATOR.getTier(), (sender, context) -> null);

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testTernaryConstructor() {
        FunctionAction action = new FunctionAction(1, (sender, context) -> null);

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuaternaryConstructor() {
        FunctionAction action = new FunctionAction(PermissionTierType.OPERATOR.getTier(), 1, (sender, context) -> null);

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuinaryConstructor() {
        FunctionAction action = new FunctionAction("test", (sender, context) -> null);

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSenaryConstructor() {
        FunctionAction action = new FunctionAction("test", PermissionTierType.OPERATOR.getTier(), (sender, context) -> null);

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSeptenaryConstructor() {
        FunctionAction action = new FunctionAction("test", 1, (sender, context) -> null);

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructor() {
        FunctionAction action = new FunctionAction("test", PermissionTierType.OPERATOR.getTier(), 1, (sender, context) -> null);

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructorWithNullIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new FunctionAction(null, PermissionTierType.OPERATOR.getTier(), 1, (sender, context) -> null));
    }

    @Test
    public void testFullConstructorWithEmptyIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new FunctionAction("", PermissionTierType.OPERATOR.getTier(), 1, (sender, context) -> null));
    }

    @Test
    public void testFullConstructorWithBlankIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new FunctionAction(" ", PermissionTierType.OPERATOR.getTier(), 1, (sender, context) -> null));
    }

    @Test
    public void testFullConstructorWithNegativePrecedence() {
        assertThrows(IllegalArgumentException.class, () -> new FunctionAction("test", PermissionTierType.OPERATOR.getTier(), -1, (sender, context) -> null));
    }

    @Test
    public void testExecuteSuccess() {
        FunctionCommanderEntity entity = new FunctionCommanderEntity();
        List<Argument<?>> arguments = new ArrayList<>() {{
            add(new Argument<>("primary", 1));
            add(new Argument<>("secondary", 2));
        }};
        FunctionAction action = new FunctionAction((sender, context) -> {
            int a = context.find("primary");
            int b = context.find("secondary");

            return a + b;
        });

        ActionItem<?> result = action.run(entity, arguments);

        assertTrue(result.getResult().isSuccess());
        assertEquals(3, result.getItem());
    }

    @Test
    public void testExecuteWithNullParams() {
        FunctionCommanderEntity entity = new FunctionCommanderEntity();
        List<Argument<?>> arguments = new ArrayList<>() {{
            add(new Argument<>("primary", "not a string"));
            add(new Argument<>("secondary", 2));
        }};
        FunctionAction action = new FunctionAction((sender, context) -> {
            int a = context.find("primary");
            int b = context.find("secondary");

            return a + b;
        });

        assertThrows(IllegalArgumentException.class, () -> action.run(null, arguments));
        assertThrows(IllegalArgumentException.class, () -> action.run(entity, null));
    }

    @Test
    public void testExecuteFailure() {
        FunctionCommanderEntity entity = new FunctionCommanderEntity();
        List<Argument<?>> arguments = new ArrayList<>() {{
            add(new Argument<>("primary", "not a string"));
            add(new Argument<>("secondary", 2));
        }};
        FunctionAction action = new FunctionAction((sender, context) -> {
            int a = context.find("primary");
            int b = context.find("secondary");

            return a + b;
        });

        ActionItem<?> result = action.run(entity, arguments);

        assertTrue(result.getResult().isFailure());
        assertEquals(ClassCastException.class, result.getItem().getClass());
    }


}
