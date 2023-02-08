package com.github.polyrocketmatt.delegate.core.command.definition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class AliasDefinitionTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    @Test
    public void testConstructor() {
        AliasDefinition definition = new AliasDefinition("test");

        assertNotNull(definition.getIdentifier());
        assertEquals("test", definition.getValue());
    }

    @Test
    public void testConstructorWithNull() {
        assertThrows(IllegalArgumentException.class, () -> new AliasDefinition(null));
    }

}
