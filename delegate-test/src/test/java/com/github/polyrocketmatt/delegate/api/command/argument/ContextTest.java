package com.github.polyrocketmatt.delegate.api.command.argument;// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.Context;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContextTest {

    private final List<Argument<?>> argumentsValid = new ArrayList<>() {{
        add(new Argument<>("arg1", "test"));
        add(new Argument<>("arg2", 1));
        add(new Argument<>("arg3", true));
    }};

    private final List<Argument<?>> argumentsInvalid = new ArrayList<>() {{
        add(new Argument<>("arg1", "test"));
        add(new Argument<>("arg2", 1));
        add(null);
    }};

    @Test
    public void testConstructor() {
        Context context = new Context(argumentsValid);

        assertEquals(3, context.size());
    }

    @Test
    public void testConstructorIllegal() {
        assertThrows(IllegalArgumentException.class, () -> new Context(null));
        assertThrows(IllegalArgumentException.class, () -> new Context(argumentsInvalid));
    }

    @Test
    public void testFind() {
        Context context = new Context(argumentsValid);

        assertEquals("test", context.find("arg1"));
        assertEquals(1, (int) context.find("arg2"));
        assertEquals(true, context.find("arg3"));

        assertNull(context.find("arg4"));
    }

    @Test
    public void testFindIllegalIdentifier() {
        Context context = new Context(argumentsValid);

        assertThrows(IllegalArgumentException.class, () -> context.find(null));
    }

    @Test
    public void testGet() {
        Context context = new Context(argumentsValid);

        assertEquals("test", context.get(0).output());
        assertEquals(1, (int) context.get(1).output());
        assertEquals(true, context.get(2).output());
    }

    @Test
    public void testGetOutOfBounds() {
        Context context = new Context(argumentsValid);

        assertThrows(IndexOutOfBoundsException.class, () -> context.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> context.get(3));
    }

}
