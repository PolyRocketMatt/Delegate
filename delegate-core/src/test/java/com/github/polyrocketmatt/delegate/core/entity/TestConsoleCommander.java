package com.github.polyrocketmatt.delegate.core.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestConsoleCommander {

    @Test
    public void testConsoleCommander() {
        ConsoleCommander commander = new ConsoleCommander();

        assertNotNull(commander);
    }

}
