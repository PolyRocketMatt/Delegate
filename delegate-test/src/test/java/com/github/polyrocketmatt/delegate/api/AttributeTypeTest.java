package com.github.polyrocketmatt.delegate.api;// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

import com.github.polyrocketmatt.delegate.api.AttributeType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttributeTypeTest {

    @Test
    public void testAttributeTypes() {
        assertEquals(8, AttributeType.values().length);
    }

}
