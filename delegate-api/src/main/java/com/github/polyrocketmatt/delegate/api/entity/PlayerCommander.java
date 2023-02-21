// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.entity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

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
    public PlayerCommander(@NotNull UUID uuid) {
        validate("uuid", UUID.class, uuid);

        this.uuid = uuid;
    }

    /**
     * Gets the unique id of the player.
     *
     * @return The unique id of the player
     */
    public @NotNull UUID getUniqueId() {
        return uuid;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PlayerCommander that)) return false;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
