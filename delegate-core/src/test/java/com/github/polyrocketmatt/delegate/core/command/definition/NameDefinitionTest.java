package com.github.polyrocketmatt.delegate.core.command.definition;

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
    }

    @Test
    public void testConstructorWithNull() {
        assertThrows(IllegalArgumentException.class, () -> new NameDefinition(null));
    }

}
