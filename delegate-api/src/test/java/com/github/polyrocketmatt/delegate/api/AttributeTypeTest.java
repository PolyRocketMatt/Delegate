// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttributeTypeTest {

    @Test
    public void testAttributeTypes() {
        assertEquals(8, AttributeType.values().length);
    }

}
