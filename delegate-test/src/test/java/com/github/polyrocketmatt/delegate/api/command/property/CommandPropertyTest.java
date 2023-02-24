package com.github.polyrocketmatt.delegate.api.command.property;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandPropertyTest {

    private static class TestCommandProperty extends CommandProperty {
        public TestCommandProperty() {
            super();
        }

        public TestCommandProperty(String id) {
            super(id);
        }

    }

    @Test
    public void testConstructorPrimary() {
        TestCommandProperty property = new TestCommandProperty();

        assertEquals(AttributeType.PROPERTY, property.getType());
        assertNotNull(property.getIdentifier());
    }

    @Test
    public void testConstructorSecondary() {
        TestCommandProperty property = new TestCommandProperty("test");

        assertEquals(AttributeType.PROPERTY, property.getType());
        assertEquals("test", property.getIdentifier());
    }

    @Test
    public void testConstructorSecondaryIllegal() {
        assertThrows(IllegalArgumentException.class, () -> new TestCommandProperty(null));
    }

}
