// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.properties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IgnoreNullPropertyTest {

    @Test
    public void testConstructor() {
        IgnoreNullProperty property = new IgnoreNullProperty();

        assertEquals("ignoreNull", property.getIdentifier());
    }

}
