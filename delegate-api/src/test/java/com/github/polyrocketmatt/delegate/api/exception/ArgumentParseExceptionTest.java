// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArgumentParseExceptionTest {

    @Test
    public void testPrimaryConstructor() {
        try {
            throw new ArgumentParseException("test", String.class);
        } catch (ArgumentParseException ex) {
            assertEquals("test", ex.getMessage());
            assertEquals(String.class, ex.getParseType());
        }
    }

    @Test
    public void testSecondaryConstructor() {
        try {
            throw new ArgumentParseException("test", new Exception(), String.class);
        } catch (ArgumentParseException ex) {
            assertEquals("test", ex.getMessage());
            assertEquals(String.class, ex.getParseType());
        }
    }

}
