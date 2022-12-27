package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.argument.StringArgument;
import com.github.polyrocketmatt.delegate.core.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.core.command.data.ActionResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandActionTest {

    @Test
    public void testSimpleAction() {
        FunctionAction action = new FunctionAction("test", (items) -> {
            System.out.printf("Hello, %s!%n", items.get(0).output());

            return null;
        });

        ActionResult result = action.run(
                new CommandBuffer<>(List.of(StringArgument.of("name", "Your name"))),
                List.of("Matt")
        );

        assertNotNull(result);
        assertTrue(result instanceof ActionItem);
        assertNull(((ActionItem<?>) result).getItem());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testActionResult() {
        FunctionAction action = new FunctionAction("test", (items) -> 3.0f);
        ActionResult result = action.run(new CommandBuffer<>(List.of()), List.of());

        assertTrue(result instanceof ActionItem);

        ActionItem<Float> item = (ActionItem<Float>) result;
        Float value = item.getItem();

        assertNotNull(result);
        assertEquals(3.0f, value);
    }

    @Test
    public void testNullActionResult() {
        FunctionAction action = new FunctionAction("test", (items) -> null);
        ActionResult result = action.run(new CommandBuffer<>(List.of()), List.of());

        assertNotNull(result);
        assertTrue(result instanceof ActionItem);
        assertNull(((ActionItem<?>) result).getItem());
    }

}
