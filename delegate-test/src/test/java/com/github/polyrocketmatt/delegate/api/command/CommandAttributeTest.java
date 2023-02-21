package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandAttributeTest {

    @Test
    public void testConstructor() {
        CommandAttribute commandAttribute = new CommandAttribute("testIdentifier") {};

        assertEquals("testIdentifier", commandAttribute.getIdentifier());
    }

    @Test
    public void testConstructorEmptyIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new CommandAttribute("") {});
    }

    @Test
    public void testConstructorBlankIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> new CommandAttribute(" ") {});
    }

}
