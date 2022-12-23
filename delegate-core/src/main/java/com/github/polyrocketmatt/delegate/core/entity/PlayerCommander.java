package com.github.polyrocketmatt.delegate.core.entity;

import java.util.UUID;

public class PlayerCommander extends CommanderEntity{

    private final UUID uuid;

    public PlayerCommander(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUniqueId() {
        return uuid;
    }

}
