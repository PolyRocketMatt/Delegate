package com.github.polyrocketmatt.delegate.core.command.argument.range;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DelegateDoubleRangeTest {

    @Test
    public void testConstructor() {
        DelegateDoubleRange delegateDoubleRange = new DelegateDoubleRange(0.0, 1.0);

        assertEquals(0.0, delegateDoubleRange.getMin());
        assertEquals(1.0, delegateDoubleRange.getMax());
    }

    @Test
    public void testContains() {
        DelegateDoubleRange delegateDoubleRange = new DelegateDoubleRange(0.0, 1.0);

        assertTrue(delegateDoubleRange.contains(0.0));
        assertTrue(delegateDoubleRange.contains(0.5));
        assertTrue(delegateDoubleRange.contains(1.0));
        assertFalse(delegateDoubleRange.contains(-1.0));
        assertFalse(delegateDoubleRange.contains(1.1));
    }

}
