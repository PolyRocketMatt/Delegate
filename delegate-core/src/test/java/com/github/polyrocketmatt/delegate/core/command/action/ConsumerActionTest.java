// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTiers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsumerActionTest {

    @Test
    public void testPrimaryConstructor() {
        ConsumerAction action = new ConsumerAction((sender, context) -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSecondaryConstructor() {
        ConsumerAction action = new ConsumerAction(PermissionTiers.OPERATOR.getTier(), (sender, context) -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testTernaryConstructor() {
        ConsumerAction action = new ConsumerAction(1, (sender, context) -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuaternaryConstructor() {
        ConsumerAction action = new ConsumerAction(PermissionTiers.OPERATOR.getTier(), 1, (sender, context) -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuinaryConstructor() {
        ConsumerAction action = new ConsumerAction("test", (sender, context) -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSenaryConstructor() {
        ConsumerAction action = new ConsumerAction("test", PermissionTiers.OPERATOR.getTier(), (sender, context) -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSeptenaryConstructor() {
        ConsumerAction action = new ConsumerAction("test", 1, (sender, context) -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructor() {
        ConsumerAction action = new ConsumerAction("test", PermissionTiers.OPERATOR.getTier(), 1, (sender, context) -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructorWithNullIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new ConsumerAction(null, PermissionTiers.OPERATOR.getTier(), 1, (sender, context) -> {}));
    }

    @Test
    public void testFullConstructorWithEmptyIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new ConsumerAction("", PermissionTiers.OPERATOR.getTier(), 1, (sender, context) -> {}));
    }

    @Test
    public void testFullConstructorWithBlankIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new ConsumerAction(" ", PermissionTiers.OPERATOR.getTier(), 1, (sender, context) -> {}));
    }

    @Test
    public void testFullConstructorWithNegativePrecedence() {
        assertThrows(IllegalArgumentException.class, () -> new ConsumerAction("test", PermissionTiers.OPERATOR.getTier(), -1, (sender, context) -> {}));
    }

    @Test
    public void testFullConstructorWithNullPermissionTier() {
        assertThrows(IllegalArgumentException.class, () -> new ConsumerAction("test", null, 1, (sender, context) -> {}));
    }

    @Test
    public void testFullConstructorWithNullAction() {
        assertThrows(IllegalArgumentException.class, () -> new ConsumerAction("test", PermissionTiers.OPERATOR.getTier(), 1, null));
    }

}
