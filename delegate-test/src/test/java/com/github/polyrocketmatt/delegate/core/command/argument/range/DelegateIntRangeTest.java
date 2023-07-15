package com.github.polyrocketmatt.delegate.core.command.argument.range;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DelegateIntRangeTest {

    @Test
    public void testConstructor() {
        DelegateIntRange delegateIntRange = new DelegateIntRange(0, 10);

        assertEquals(0, delegateIntRange.getMin());
        assertEquals(10, delegateIntRange.getMax());
    }

    @Test
    public void testContains() {
        DelegateIntRange delegateIntRange = new DelegateIntRange(0, 10);

        assertTrue(delegateIntRange.contains(0));
        assertTrue(delegateIntRange.contains(5));
        assertTrue(delegateIntRange.contains(10));
        assertFalse(delegateIntRange.contains(-1));
        assertFalse(delegateIntRange.contains(11));
    }

    @Test
    public void testEquals() {
        DelegateIntRange rangeA = new DelegateIntRange(0, 10);
        DelegateIntRange rangeB = new DelegateIntRange(0, 10);
        DelegateIntRange rangeC = new DelegateIntRange(1, 2);

        assertEquals(rangeA, rangeB);
        assertNotEquals(rangeA, rangeC);
    }

}
