// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class NameDefinitionTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    @Test
    public void testConstructor() {
        NameDefinition definition = new NameDefinition("testCommand");

        assertEquals("commandName", definition.getIdentifier());
        assertEquals("testCommand", definition.getValue());
        assertEquals(AttributeType.DEFINITION, definition.getType());
    }

    @Test
    public void testConstructorWithNull() {
        assertThrows(IllegalArgumentException.class, () -> new NameDefinition(null));
    }

}
