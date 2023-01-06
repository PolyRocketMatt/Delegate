package com.github.polyrocketmatt.delegate.api.entity;

import java.util.UUID;

/**
 * Represents a command issuer as a player.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public abstract class PlayerCommander implements CommanderEntity {

    private final UUID uuid;

    /**
     * Creates a new {@link PlayerCommander} with the given unique id
     * of the player that issued a command.
     *
     * @param uuid The unique id of the player that issued a command.
     */
    public PlayerCommander(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the unique id of the player.
     *
     * @return The unique id of the player
     */
    public UUID getUniqueId() {
        return uuid;
    }

}
