package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTiers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RunnableActionTest {

    @Test
    public void testPrimaryConstructor() {
        RunnableAction action = new RunnableAction(() -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSecondaryConstructor() {
        RunnableAction action = new RunnableAction(PermissionTiers.OPERATOR.getTier(), () -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testTernaryConstructor() {
        RunnableAction action = new RunnableAction(1, () -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuaternaryConstructor() {
        RunnableAction action = new RunnableAction(PermissionTiers.OPERATOR.getTier(), 1, () -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuinaryConstructor() {
        RunnableAction action = new RunnableAction("test", () -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSenaryConstructor() {
        RunnableAction action = new RunnableAction("test", PermissionTiers.OPERATOR.getTier(), () -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSeptenaryConstructor() {
        RunnableAction action = new RunnableAction("test", 1, () -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructor() {
        RunnableAction action = new RunnableAction("test", PermissionTiers.OPERATOR.getTier(), 1, () -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructorWithNullIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new RunnableAction(null, PermissionTiers.OPERATOR.getTier(), 1, () -> {}));
    }

    @Test
    public void testFullConstructorWithEmptyIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new RunnableAction("", PermissionTiers.OPERATOR.getTier(), 1, () -> {}));
    }

    @Test
    public void testFullConstructorWithBlankIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new RunnableAction(" ", PermissionTiers.OPERATOR.getTier(), 1, () -> {}));
    }

    @Test
    public void testFullConstructorWithNegativePrecedence() {
        assertThrows(IllegalArgumentException.class, () -> new RunnableAction("test", PermissionTiers.OPERATOR.getTier(), -1, () -> {}));
    }

    @Test
    public void testFullConstructorWithNullPermissionTier() {
        assertThrows(IllegalArgumentException.class, () -> new RunnableAction("test", null, 1, () -> {}));
    }

    @Test
    public void testFullConstructorWithNullAction() {
        assertThrows(IllegalArgumentException.class, () -> new RunnableAction("test", PermissionTiers.OPERATOR.getTier(), 1, null));
    }

}
