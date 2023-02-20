// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.properties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsyncPropertyTest {

    @Test
    public void testConstructor() {
        AsyncProperty property = new AsyncProperty();

        assertEquals("executeAsync", property.getIdentifier());
    }

}
