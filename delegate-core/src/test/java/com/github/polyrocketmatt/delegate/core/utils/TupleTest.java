// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TupleTest {

    @Test
    public void testRegularTuple() {
        Tuple<Integer, Integer> tuple = new Tuple<>(1, 2);

        assertEquals(1, tuple.a());
        assertEquals(2, tuple.b());
    }

    @Test
    public void testTupleWithNull() {
        Tuple<Integer, Integer> tuple = new Tuple<>(null, 2);

        assertNull(tuple.a());
        assertEquals(2, tuple.b());
    }

    @Test
    public void testTupleWithAllNull() {
        Tuple<Integer, Integer> tuple = new Tuple<>(null, null);

        assertNull(tuple.a());
        assertNull(tuple.b());
    }

}
