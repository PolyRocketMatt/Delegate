package com.github.polyrocketmatt.delegate.impl.entity;

import com.github.polyrocketmatt.delegate.api.entity.PlayerCommander;
import com.github.polyrocketmatt.delegate.impl.Delegate;
import com.github.polyrocketmatt.delegate.impl.text.ColorConverter;
import com.velocitypowered.api.proxy.Player;

import java.util.Optional;
import java.util.UUID;

public class VelocityPlayerCommander extends PlayerCommander {

    private final Player player;

    public VelocityPlayerCommander(Player player) {
        super(player.getUniqueId());
        this.player = player;
    }

    @SuppressWarnings("")
    public VelocityPlayerCommander(UUID uuid) {
        super(uuid);
        Optional<Player> optionalPlayer = Delegate.getProxy().getPlayer(uuid);
        if (optionalPlayer.isEmpty())
            throw new IllegalArgumentException("Player with UUID %s was not found, but is required for this constructor".formatted(uuid.toString()));
        this.player = optionalPlayer.get();
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.player.hasPermission(permission);
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public void sendMessage(String message) {
        this.player.sendMessage(ColorConverter.convert(message));
    }

}
