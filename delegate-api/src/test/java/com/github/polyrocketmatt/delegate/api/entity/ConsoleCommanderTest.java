package com.github.polyrocketmatt.delegate.api.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsoleCommanderTest {

    private final ConsoleCommander consoleCommander = new ConsoleCommander();

    @Test
    public void testHasPermission() {
        assertTrue(consoleCommander.hasPermission("any.permission"));
    }

    @Test
    public void testIsOperator() {
        assertTrue(consoleCommander.isOperator());
    }

    @Test
    public void testSendMessage() {
        assertDoesNotThrow(() -> consoleCommander.sendMessage("Hello, World!"));
    }

}
