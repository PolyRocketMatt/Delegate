package com.github.polyrocketmatt.delegate.core.command.trigger;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SuccessTriggerTest {

    private final List<ActionItem.Result> validList = List.of(
            ActionItem.Result.SUCCESS,
            ActionItem.Result.SUCCESS
    );

    private final List<ActionItem.Result> invalidList = List.of(
            ActionItem.Result.SUCCESS,
            ActionItem.Result.FAILURE
    );

    @Test
    public void testConstructor() {
        SuccessTrigger trigger = new SuccessTrigger((info, capture) -> {});

        assertNotNull(trigger.getIdentifier());
        assertEquals(AttributeType.TRIGGER, trigger.getType());
    }

    @Test
    public void testConstructorNull() {
        assertThrows(IllegalArgumentException.class, () -> new SuccessTrigger(null));
    }

    @Test
    public void testShouldTrigger() {
        SuccessTrigger trigger = new SuccessTrigger((info, capture) -> {});

        assertTrue(trigger.shouldTrigger(validList));
        assertFalse(trigger.shouldTrigger(invalidList));
    }

}
