package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTierType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class RunnableActionTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    private static class RunnableCommanderEntity implements CommanderEntity {

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
            System.out.println(message);
        }

    }

    @Test
    public void testPrimaryConstructor() {
        RunnableAction action = new RunnableAction(() -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSecondaryConstructor() {
        RunnableAction action = new RunnableAction(PermissionTierType.OPERATOR.getTier(), () -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testTernaryConstructor() {
        RunnableAction action = new RunnableAction(1, () -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuaternaryConstructor() {
        RunnableAction action = new RunnableAction(PermissionTierType.OPERATOR.getTier(), 1, () -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testQuinaryConstructor() {
        RunnableAction action = new RunnableAction("test", () -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSenaryConstructor() {
        RunnableAction action = new RunnableAction("test", PermissionTierType.OPERATOR.getTier(), () -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(0, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testSeptenaryConstructor() {
        RunnableAction action = new RunnableAction("test", 1, () -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.GLOBAL.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructor() {
        RunnableAction action = new RunnableAction("test", PermissionTierType.OPERATOR.getTier(), 1, () -> {});

        assertEquals("test", action.getIdentifier());
        assertEquals(1, action.getPrecedence());
        assertEquals(AttributeType.ACTION, action.getType());
        assertEquals(PermissionTierType.OPERATOR.getTier(), action.getPermissionTier());
    }

    @Test
    public void testFullConstructorWithNullIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new RunnableAction(null, PermissionTierType.OPERATOR.getTier(), 1, () -> {}));
    }

    @Test
    public void testFullConstructorWithEmptyIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new RunnableAction("", PermissionTierType.OPERATOR.getTier(), 1, () -> {}));
    }

    @Test
    public void testFullConstructorWithBlankIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new RunnableAction(" ", PermissionTierType.OPERATOR.getTier(), 1, () -> {}));
    }

    @Test
    public void testFullConstructorWithNegativePrecedence() {
        assertThrows(IllegalArgumentException.class, () -> new RunnableAction("test", PermissionTierType.OPERATOR.getTier(), -1, () -> {}));
    }

    @Test
    public void testFullConstructorWithNullPermissionTier() {
        assertThrows(IllegalArgumentException.class, () -> new RunnableAction("test", null, 1, () -> {}));
    }

    @Test
    public void testFullConstructorWithNullAction() {
        assertThrows(IllegalArgumentException.class, () -> new RunnableAction("test", PermissionTierType.OPERATOR.getTier(), 1, null));
    }

    @Test
    public void testExecuteSuccess() {
        RunnableCommanderEntity entity = new RunnableCommanderEntity();
        List<Argument<?>> arguments = new ArrayList<>() {{
            add(new Argument<>("primary", 1));
            add(new Argument<>("secondary", 2));
        }};
        RunnableAction action = new RunnableAction(() -> System.out.println("Hello, World!"));
        ActionItem<?> result = action.run(entity, arguments);

        assertTrue(result.getResult().isSuccess());
    }

    @Test
    public void testExecuteWithNullParams() {
        RunnableCommanderEntity entity = new RunnableCommanderEntity();
        List<Argument<?>> arguments = new ArrayList<>() {{
            add(new Argument<>("primary", "not a string"));
            add(new Argument<>("secondary", 2));
        }};
        RunnableAction action = new RunnableAction(() -> {
            throw new IllegalStateException("This should not be thrown.");
        });

        assertThrows(IllegalArgumentException.class, () -> action.run(null, arguments));
        assertThrows(IllegalArgumentException.class, () -> action.run(entity, null));
    }

    @Test
    public void testExecuteFailure() {
        RunnableCommanderEntity entity = new RunnableCommanderEntity();
        List<Argument<?>> arguments = new ArrayList<>() {{
            add(new Argument<>("primary", "not a string"));
            add(new Argument<>("secondary", 2));
        }};
        RunnableAction action = new RunnableAction(() -> {
            throw new IllegalStateException("This should not be thrown.");
        });

        ActionItem<?> result = action.run(entity, arguments);

        assertTrue(result.getResult().isFailure());
        assertEquals(IllegalStateException.class, result.getItem().getClass());
    }

}
