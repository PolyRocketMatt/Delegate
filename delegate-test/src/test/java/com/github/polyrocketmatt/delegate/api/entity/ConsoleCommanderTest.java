package com.github.polyrocketmatt.delegate.api.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleCommanderTest {

    private final ConsoleCommander consoleCommander = new ConsoleCommander();

    @Test
    public void testHasPermission() {
        assertTrue(consoleCommander.hasPermission("any.permission"));
        assertThrows(IllegalArgumentException.class, () -> consoleCommander.hasPermission(null));
    }

    @Test
    public void testIsOperator() {
        assertTrue(consoleCommander.isOperator());
    }

    @Test
    public void testSendMessage() {
        assertDoesNotThrow(() -> consoleCommander.sendMessage("Hello, World!"));
        assertThrows(IllegalArgumentException.class, () -> consoleCommander.sendMessage(null));
    }

    @Test
    public void testIsPlayer() {
        assertFalse(consoleCommander.isPlayer());
    }

}
