package com.github.polyrocketmatt.delegate.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TupleTest {

    @Test
    public void testTupleNull() {
        Tuple<String, String> tuple = new Tuple<>(null, null);

        assertNull(tuple.getA());
        assertNull(tuple.getB());
    }

    @Test
    public void testTuplePrimaryNull() {
        Tuple<String, String> tuple = new Tuple<>(null, "test");

        assertNull(tuple.getA());
        assertNotNull(tuple.getB());
        assertEquals("test", tuple.getB());
    }

    @Test
    public void testTupleSecondaryNull() {
        Tuple<String, String> tuple = new Tuple<>("test", null);

        assertNotNull(tuple.getA());
        assertNull(tuple.getB());
        assertEquals("test", tuple.getA());
    }

    @Test
    public void testTuple() {
        Tuple<String, String> tuple = new Tuple<>("testA", "testB");

        assertNotNull(tuple.getA());
        assertNotNull(tuple.getB());

        assertEquals("testA", tuple.getA());
        assertEquals("testB", tuple.getB());
    }

}
