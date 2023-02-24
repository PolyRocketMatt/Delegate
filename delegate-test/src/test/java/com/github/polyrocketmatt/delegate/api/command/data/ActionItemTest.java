// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActionItemTest {

    @Test
    public void testConstructor() {
        ActionItem<String> item = new ActionItem<>(ActionItem.Result.SUCCESS, "test");

        assertEquals(ActionItem.Result.SUCCESS, item.getResult());
        assertEquals("test", item.getItem());
    }

    @Test
    public void testConstructorIllegal() {
        assertThrows(IllegalArgumentException.class, () -> new ActionItem<>(null, "test"));
    }

    @Test
    public void testConstructorNull() {
        ActionItem<String> item = new ActionItem<>(ActionItem.Result.SUCCESS, null);

        assertEquals(ActionItem.Result.SUCCESS, item.getResult());
        assertNull(item.getItem());
    }

    @Test
    public void testResults() {
        assertTrue(ActionItem.Result.SUCCESS.isSuccess());
        assertFalse(ActionItem.Result.SUCCESS.isFailure());

        assertFalse(ActionItem.Result.SUCCESS.isFailure());
        assertTrue(ActionItem.Result.SUCCESS.isSuccess());
    }

}
