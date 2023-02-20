// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttributeExceptionTest {

    @Test
    public void testConstructor() {
        try {
            throw new AttributeException("test");
        } catch (AttributeException ex) {
            assertEquals("test", ex.getMessage());
        }
    }

}
