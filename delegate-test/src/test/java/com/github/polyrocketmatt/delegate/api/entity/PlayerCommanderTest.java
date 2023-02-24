package com.github.polyrocketmatt.delegate.api.entity;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerCommanderTest {

    private final UUID uuid = UUID.randomUUID();
    private final PlayerCommander playerCommander = new PlayerCommander(uuid) {
        @Override
        public boolean hasPermission(@NotNull String permission) {
            return permission.equals("any.permission");
        }

        @Override
        public boolean isOperator() {
            return false;
        }

        @Override
        public void sendMessage(@NotNull String message) {
            System.out.println(message);
        }
    };

    @Test
    public void testConstructor() {
        assertEquals(uuid, playerCommander.getUniqueId());
    }

    @Test
    public void testConstructorIllegal() {
        assertThrows(IllegalArgumentException.class, () -> new PlayerCommander(null) {
            @Override
            public boolean hasPermission(@NotNull String permission) {
                return permission.equals("any.permission");
            }

            @Override
            public boolean isOperator() {
                return false;
            }

            @Override
            public void sendMessage(@NotNull String message) {
                System.out.println(message);
            }
        });
    }

    @Test
    public void testHasPermission() {
        assertTrue(playerCommander.hasPermission("any.permission"));
        assertFalse(playerCommander.hasPermission("any.other.permission"));
    }

    @Test
    public void testIsOperator() {
        assertFalse(playerCommander.isOperator());
    }

    @Test
    public void testSendMessage() {
        assertDoesNotThrow(() -> playerCommander.sendMessage("Hello, World!"));
    }

    @Test
    public void testEqualsAndHashCode() {
        PlayerCommander comparison = new PlayerCommander(uuid) {
            @Override
            public boolean hasPermission(@NotNull String permission) {
                return false;
            }

            @Override
            public boolean isOperator() {
                return false;
            }

            @Override
            public void sendMessage(@NotNull String message) {

            }
        };

        assertEquals(playerCommander, comparison);
        assertEquals(playerCommander.hashCode(), comparison.hashCode());
    }

}
