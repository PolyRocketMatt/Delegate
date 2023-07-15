package com.github.polyrocketmatt.delegate.core.command.argument.range;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DelegateFloatRangeTest {

    @Test
    public void testConstructor() {
        DelegateFloatRange delegateFloatRange = new DelegateFloatRange(0.0f, 1.0f);

        assertEquals(0.0f, delegateFloatRange.getMin());
        assertEquals(1.0f, delegateFloatRange.getMax());
    }

    @Test
    public void testContains() {
        DelegateFloatRange delegateFloatRange = new DelegateFloatRange(0.0f, 1.0f);

        assertTrue(delegateFloatRange.contains(0.0f));
        assertTrue(delegateFloatRange.contains(0.5f));
        assertTrue(delegateFloatRange.contains(1.0f));
        assertFalse(delegateFloatRange.contains(-1.0f));
        assertFalse(delegateFloatRange.contains(1.1f));
    }

}
