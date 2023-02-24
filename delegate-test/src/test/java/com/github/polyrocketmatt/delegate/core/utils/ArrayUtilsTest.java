// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayUtilsTest {

    private interface TestInterface { }
    private static class A implements TestInterface {
        private final int value;

        public A(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof A && ((A) obj).value == value;
        }
    }
    private static class B implements TestInterface {

        private final double value;

        public B(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof B && ((B) obj).value == value;
        }
    }

    @Test
    public void testInterfaceCombine() {
        List<A> aList = new ArrayList<>();
        aList.add(new A(1));
        aList.add(new A(2));

        List<B> bList = new ArrayList<>();
        bList.add(new B(1.0));
        bList.add(new B(2.0));

        List<TestInterface> combined = ArrayUtils.combine(aList, bList);

        assertEquals(4, combined.size());
        assertEquals(new A(1), combined.get(0));
        assertEquals(new A(2), combined.get(1));
        assertEquals(new B(1.0), combined.get(2));
        assertEquals(new B(2.0), combined.get(3));
    }

    @Test
    public void testInterfaceCombineWithNull() {
        List<A> aList = new ArrayList<>();
        aList.add(new A(1));
        aList.add(null);

        List<B> bList = new ArrayList<>();
        bList.add(new B(1.0));
        bList.add(new B(2.0));

        List<TestInterface> combined = ArrayUtils.combine(aList, bList);

        assertEquals(4, combined.size());
        assertEquals(new A(1), combined.get(0));
        assertNull(combined.get(1));
        assertEquals(new B(1.0), combined.get(2));
        assertEquals(new B(2.0), combined.get(3));
    }

    @Test
    public void testInterfaceCombineAllNull() {
        List<A> aList = new ArrayList<>();
        aList.add(null);

        List<B> bList = new ArrayList<>();
        bList.add(null);

        List<TestInterface> combined = ArrayUtils.combine(aList, bList);

        assertEquals(2, combined.size());
        assertNull(combined.get(0));
        assertNull(combined.get(1));
    }

}
