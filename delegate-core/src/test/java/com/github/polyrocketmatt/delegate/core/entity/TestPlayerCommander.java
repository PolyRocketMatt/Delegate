package com.github.polyrocketmatt.delegate.core.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayerCommander {

    @Test
    public void testPlayerCommander() {
        UUID uuid = UUID.randomUUID();
        PlayerCommander commander = new PlayerCommander(uuid);

        assertNotNull(commander);
        assertNotNull(commander.getUniqueId());
        assertEquals(uuid, commander.getUniqueId());
    }

}
