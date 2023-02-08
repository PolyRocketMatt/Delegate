// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTiers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class FunctionActionTest {

    @Test
    public void testPrimaryConstructor() {
        FunctionAction action = new FunctionAction((sender, context) -> null);

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSecondaryConstructor() {
        FunctionAction action = new FunctionAction(PermissionTiers.OPERATOR.getTier(), (sender, context) -> null);

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testTernaryConstructor() {
        FunctionAction action = new FunctionAction(1, (sender, context) -> null);

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuaternaryConstructor() {
        FunctionAction action = new FunctionAction(PermissionTiers.OPERATOR.getTier(), 1, (sender, context) -> null);

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuinaryConstructor() {
        FunctionAction action = new FunctionAction("test", (sender, context) -> null);

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSenaryConstructor() {
        FunctionAction action = new FunctionAction("test", PermissionTiers.OPERATOR.getTier(), (sender, context) -> null);

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSeptenaryConstructor() {
        FunctionAction action = new FunctionAction("test", 1, (sender, context) -> null);

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructor() {
        FunctionAction action = new FunctionAction("test", PermissionTiers.OPERATOR.getTier(), 1, (sender, context) -> null);

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructorWithNullIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new FunctionAction(null, PermissionTiers.OPERATOR.getTier(), 1, (sender, context) -> null));
    }

    @Test
    public void testFullConstructorWithEmptyIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new FunctionAction("", PermissionTiers.OPERATOR.getTier(), 1, (sender, context) -> null));
    }

    @Test
    public void testFullConstructorWithBlankIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new FunctionAction(" ", PermissionTiers.OPERATOR.getTier(), 1, (sender, context) -> null));
    }

    @Test
    public void testFullConstructorWithNegativePrecedence() {
        assertThrows(IllegalArgumentException.class, () -> new FunctionAction("test", PermissionTiers.OPERATOR.getTier(), -1, (sender, context) -> null));
    }

    @Test
    public void testFullConstructorWithNullPermissionTier() {
        assertThrows(IllegalArgumentException.class, () -> new FunctionAction("test", null, 1, (sender, context) -> null));
    }

    @Test
    public void testFullConstructorWithNullAction() {
        assertThrows(IllegalArgumentException.class, () -> new FunctionAction("test", PermissionTiers.OPERATOR.getTier(), 1, null));
    }

}
