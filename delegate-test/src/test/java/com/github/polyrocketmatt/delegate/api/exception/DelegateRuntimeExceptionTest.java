package com.github.polyrocketmatt.delegate.api.exception;// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

import com.github.polyrocketmatt.delegate.api.exception.DelegateRuntimeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DelegateRuntimeExceptionTest {

    @Test
    public void testConstructor() {
        Exception cause = new Exception();

        try {
            throw new DelegateRuntimeException("Test", cause);
        } catch (DelegateRuntimeException ex) {
            assertEquals("Test", ex.getMessage());
            assertEquals(cause, ex.getCause());
        }
    }

}
