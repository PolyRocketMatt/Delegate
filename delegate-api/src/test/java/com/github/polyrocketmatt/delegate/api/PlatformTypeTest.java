// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlatformTypeTest {

    @Test
    public void testPlatformTypes() {
        assertEquals(4, PlatformType.values().length);
    }

}
