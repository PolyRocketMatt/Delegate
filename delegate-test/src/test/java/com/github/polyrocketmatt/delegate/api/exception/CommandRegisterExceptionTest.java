package com.github.polyrocketmatt.delegate.api.exception;// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandRegisterExceptionTest {

    @Test
    public void testPrimaryConstructor() {
        try {
            throw new CommandRegisterException("test");
        } catch (CommandRegisterException ex) {
            assertEquals("test", ex.getMessage());
        }
    }

    @Test
    public void testSecondaryConstructor() {
        Exception cause = new Exception();

        try {
            throw new CommandRegisterException("test", cause);
        } catch (CommandRegisterException ex) {
            assertEquals("test", ex.getMessage());
            assertEquals(cause, ex.getCause());
        }
    }

}
