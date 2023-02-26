// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.core.command.TestCommandBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class SubcommandDefinitionTest {

    private TestCommandBuilder builder;

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);

        this.builder = new TestCommandBuilder();
    }

    @Test
    public void testConstructorPrimary() {
        SubcommandDefinition definition = new SubcommandDefinition(builder);

        assertNotNull(definition.getIdentifier());
        assertNotNull(definition.getValue());
        assertEquals(AttributeType.DEFINITION, definition.getType());
    }

    @Test
    public void testConstructorPrimaryWithNull() {
        assertThrows(IllegalArgumentException.class, () -> new SubcommandDefinition(null));
    }

    @Test
    public void testConstructorSecondary() {
        SubcommandDefinition definition = new SubcommandDefinition("test", "A simple test description");

        assertEquals("test", definition.getIdentifier());
        assertNotNull(definition.getValue());
        assertEquals(AttributeType.DEFINITION, definition.getType());
    }

}
