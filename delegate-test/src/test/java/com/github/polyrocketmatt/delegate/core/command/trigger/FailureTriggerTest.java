package com.github.polyrocketmatt.delegate.core.command.trigger;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FailureTriggerTest {

    private final List<ActionItem.Result> validList = List.of(
            ActionItem.Result.FAILURE,
            ActionItem.Result.FAILURE
    );

    private final List<ActionItem.Result> invalidList = List.of(
            ActionItem.Result.SUCCESS,
            ActionItem.Result.FAILURE
    );

    @Test
    public void testConstructor() {
        FailureTrigger trigger = new FailureTrigger((info, capture) -> {});

        assertNotNull(trigger.getIdentifier());
        assertEquals(AttributeType.TRIGGER, trigger.getType());
    }

    @Test
    public void testConstructorNull() {
        assertThrows(IllegalArgumentException.class, () -> new FailureTrigger(null));
    }

    @Test
    public void testShouldTrigger() {
        FailureTrigger trigger = new FailureTrigger((info, capture) -> {});

        assertTrue(trigger.shouldTrigger(validList));
        assertFalse(trigger.shouldTrigger(invalidList));
    }

}
