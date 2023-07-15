package com.github.polyrocketmatt.delegate.core.command.argument.range;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DelegateLongRangeTest {

    @Test
    public void testConstructor() {
        DelegateLongRange delegateLongRange = new DelegateLongRange(0L, 10L);

        assertEquals(0L, delegateLongRange.getMin());
        assertEquals(10L, delegateLongRange.getMax());
    }

    @Test
    public void testContains() {
        DelegateLongRange delegateLongRange = new DelegateLongRange(0L, 10L);

        assertTrue(delegateLongRange.contains(0L));
        assertTrue(delegateLongRange.contains(5L));
        assertTrue(delegateLongRange.contains(10L));
        assertFalse(delegateLongRange.contains(-1L));
        assertFalse(delegateLongRange.contains(11L));
    }

}
