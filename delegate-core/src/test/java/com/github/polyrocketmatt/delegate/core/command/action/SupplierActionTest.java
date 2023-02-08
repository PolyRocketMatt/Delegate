package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTiers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SupplierActionTest {

    @Test
    public void testPrimaryConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>(() -> true);

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSecondaryConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>(PermissionTiers.OPERATOR.getTier(), () -> true);

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testTernaryConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>(1, () -> true);

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuaternaryConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>(PermissionTiers.OPERATOR.getTier(), 1, () -> true);

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuinaryConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>("test", () -> true);

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSenaryConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>("test", PermissionTiers.OPERATOR.getTier(), () -> true);

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSeptenaryConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>("test", 1, () -> true);

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>("test", PermissionTiers.OPERATOR.getTier(), 1, () -> true);

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(PermissionTiers.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructorWithNullIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new SupplierAction<>(null, PermissionTiers.OPERATOR.getTier(), 1, () -> true));
    }

    @Test
    public void testFullConstructorWithEmptyIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new SupplierAction<>("", PermissionTiers.OPERATOR.getTier(), 1, () -> true));
    }

    @Test
    public void testFullConstructorWithBlankIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new SupplierAction<>(" ", PermissionTiers.OPERATOR.getTier(), 1, () -> true));
    }

    @Test
    public void testFullConstructorWithNegativePrecedence() {
        assertThrows(IllegalArgumentException.class, () -> new SupplierAction<>("test", PermissionTiers.OPERATOR.getTier(), -1, () -> true));
    }

    @Test
    public void testFullConstructorWithNullPermissionTier() {
        assertThrows(IllegalArgumentException.class, () -> new SupplierAction<>("test", null, 1, () -> true));
    }

    @Test
    public void testFullConstructorWithNullAction() {
        assertThrows(IllegalArgumentException.class, () -> new SupplierAction<>("test", PermissionTiers.OPERATOR.getTier(), 1, null));
    }

}
