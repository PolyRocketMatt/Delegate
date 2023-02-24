// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.argument;

import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArgumentParserTest {

    private static class ArgumentParserImpl implements ArgumentParser<String> { }

    @Test
    public void testDefaultParser() {
        ArgumentParser<String> parser = new ArgumentParserImpl();
        Argument<String> argument = parser.parse("test");

        assertNotNull(argument, "Argument should not be null");
        assertNotNull(argument.identifier());
        assertNull(argument.output());
    }

    @Test
    public void testDefaultOnFail() {
        ArgumentParser<String> parser = new ArgumentParserImpl();

        assertEquals(ArgumentParseException.class, parser.onFail("test", new Exception("wrapped"), String.class).getClass());
        assertEquals(ArgumentParseException.class, parser.onFail("test", null, String.class).getClass());
    }

}
