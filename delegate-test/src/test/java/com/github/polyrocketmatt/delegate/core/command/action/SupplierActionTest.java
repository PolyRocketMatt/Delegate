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

public class SupplierActionTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    private static class SupplierCommanderEntity implements CommanderEntity {

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
        SupplierAction<Boolean> action = new SupplierAction<>(() -> true);

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSecondaryConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>(PermissionTierType.OPERATOR.getTier(), () -> true);

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testTernaryConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>(1, () -> true);

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuaternaryConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>(PermissionTierType.OPERATOR.getTier(), 1, () -> true);

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuinaryConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>("test", () -> true);

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSenaryConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>("test", PermissionTierType.OPERATOR.getTier(), () -> true);

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSeptenaryConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>("test", 1, () -> true);

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructor() {
        SupplierAction<Boolean> action = new SupplierAction<>("test", PermissionTierType.OPERATOR.getTier(), 1, () -> true);

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructorWithNullIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new SupplierAction<>(null, PermissionTierType.OPERATOR.getTier(), 1, () -> true));
    }

    @Test
    public void testFullConstructorWithEmptyIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new SupplierAction<>("", PermissionTierType.OPERATOR.getTier(), 1, () -> true));
    }

    @Test
    public void testFullConstructorWithBlankIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new SupplierAction<>(" ", PermissionTierType.OPERATOR.getTier(), 1, () -> true));
    }

    @Test
    public void testFullConstructorWithNegativePrecedence() {
        assertThrows(IllegalArgumentException.class, () -> new SupplierAction<>("test", PermissionTierType.OPERATOR.getTier(), -1, () -> true));
    }

    @Test
    public void testExecuteSuccess() {
        SupplierCommanderEntity entity = new SupplierCommanderEntity();
        List<Argument<?>> arguments = new ArrayList<>() {{
            add(new Argument<>("primary", 1));
            add(new Argument<>("secondary", 2));
        }};
        SupplierAction<String> action = new SupplierAction<>(() -> "Hello, World");
        ActionItem<?> result = action.run(entity, arguments);

        assertTrue(result.getResult().isSuccess());
        assertEquals("Hello, World", result.getItem());
    }

    @Test
    public void testExecuteWithNullParams() {
        SupplierCommanderEntity entity = new SupplierCommanderEntity();
        List<Argument<?>> arguments = new ArrayList<>() {{
            add(new Argument<>("primary", 1));
            add(new Argument<>("secondary", 2));
        }};
        SupplierAction<String> action = new SupplierAction<>(() -> "Hello, World");

        assertThrows(IllegalArgumentException.class, () -> action.run(null, arguments));
        assertThrows(IllegalArgumentException.class, () -> action.run(entity, null));
    }

    @Test
    public void testExecuteFailure() {
        SupplierCommanderEntity entity = new SupplierCommanderEntity();
        List<Argument<?>> arguments = new ArrayList<>() {{
            add(new Argument<>("primary", "not a string"));
            add(new Argument<>("secondary", 2));
        }};
        SupplierAction<String> action = new SupplierAction<>(() -> {
            throw new IllegalStateException("This should not be thrown");
        });
        ActionItem<?> result = action.run(entity, arguments);

        assertTrue(result.getResult().isFailure());
        assertEquals(IllegalStateException.class, result.getItem().getClass());
    }

}
