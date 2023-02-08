package com.github.polyrocketmatt.delegate.core.command.definition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class DescriptionDefinitionTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    @Test
    public void testConstructor() {
        DescriptionDefinition definition = new DescriptionDefinition("This is a description");

        assertEquals("commandDescription", definition.getIdentifier());
        assertEquals("This is a description", definition.getValue());
    }

    @Test
    public void testConstructorWithNull() {
        assertThrows(IllegalArgumentException.class, () -> new DescriptionDefinition(null));
    }

}
