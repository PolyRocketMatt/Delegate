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

public class ConsumerActionTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    private static class ConsumerCommanderEntity implements CommanderEntity {

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
        ConsumerAction action = new ConsumerAction((sender, context) -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSecondaryConstructor() {
        ConsumerAction action = new ConsumerAction(PermissionTierType.OPERATOR.getTier(), (sender, context) -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testTernaryConstructor() {
        ConsumerAction action = new ConsumerAction(1, (sender, context) -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuaternaryConstructor() {
        ConsumerAction action = new ConsumerAction(PermissionTierType.OPERATOR.getTier(), 1, (sender, context) -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuinaryConstructor() {
        ConsumerAction action = new ConsumerAction("test", (sender, context) -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSenaryConstructor() {
        ConsumerAction action = new ConsumerAction("test", PermissionTierType.OPERATOR.getTier(), (sender, context) -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSeptenaryConstructor() {
        ConsumerAction action = new ConsumerAction("test", 1, (sender, context) -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructor() {
        ConsumerAction action = new ConsumerAction("test", PermissionTierType.OPERATOR.getTier(), 1, (sender, context) -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructorWithNullIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new ConsumerAction(null, PermissionTierType.OPERATOR.getTier(), 1, (sender, context) -> {}));
    }

    @Test
    public void testFullConstructorWithEmptyIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new ConsumerAction("", PermissionTierType.OPERATOR.getTier(), 1, (sender, context) -> {}));
    }

    @Test
    public void testFullConstructorWithBlankIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new ConsumerAction(" ", PermissionTierType.OPERATOR.getTier(), 1, (sender, context) -> {}));
    }

    @Test
    public void testFullConstructorWithNegativePrecedence() {
        assertThrows(IllegalArgumentException.class, () -> new ConsumerAction("test", PermissionTierType.OPERATOR.getTier(), -1, (sender, context) -> {}));
    }

    @Test
    public void testExecuteSuccess() {
        ConsumerCommanderEntity entity = new ConsumerCommanderEntity();
        List<Argument<?>> arguments = new ArrayList<>() {{
            add(new Argument<>("primary", 1));
            add(new Argument<>("secondary", 2));
        }};
        ConsumerAction action = new ConsumerAction((sender, context) -> {
            int a = context.find("primary");
            int b = context.find("secondary");

            sender.sendMessage("" + (a + b));
        });

        ActionItem<?> result = action.run(entity, arguments);

        assertTrue(result.getResult().isSuccess());
    }

    @Test
    public void testExecuteWithNullParams() {
        ConsumerCommanderEntity entity = new ConsumerCommanderEntity();
        List<Argument<?>> arguments = new ArrayList<>() {{
            add(new Argument<>("primary", "not a string"));
            add(new Argument<>("secondary", 2));
        }};
        ConsumerAction action = new ConsumerAction((sender, context) -> {
            int a = context.find("primary");
            int b = context.find("secondary");

            sender.sendMessage("" + (a + b));
        });

        assertThrows(IllegalArgumentException.class, () -> action.run(null, arguments));
        assertThrows(IllegalArgumentException.class, () -> action.run(entity, null));
    }

    @Test
    public void testExecuteFailure() {
        ConsumerCommanderEntity entity = new ConsumerCommanderEntity();
        List<Argument<?>> arguments = new ArrayList<>() {{
            add(new Argument<>("primary", "not a string"));
            add(new Argument<>("secondary", 2));
        }};
        ConsumerAction action = new ConsumerAction((sender, context) -> {
            int a = context.find("primary");
            int b = context.find("secondary");

            sender.sendMessage("" + (a + b));
        });

        ActionItem<?> result = action.run(entity, arguments);

        assertTrue(result.getResult().isFailure());
        assertEquals(ClassCastException.class, result.getItem().getClass());
    }

}
