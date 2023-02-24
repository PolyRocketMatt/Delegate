// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.impl.entity;

import com.github.polyrocketmatt.delegate.api.entity.PlayerCommander;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

public class BukkitPlayerCommander extends PlayerCommander {

    private final Player player;
    private final Set<PermissionAttachmentInfo> permissionAttachmentInfos;

    public BukkitPlayerCommander(Player player) {
        super(player.getUniqueId());
        this.player = player;
        this.permissionAttachmentInfos = player.getEffectivePermissions();
    }

    @SuppressWarnings("")
    public BukkitPlayerCommander(UUID uuid) {
        super(uuid);
        this.player = Bukkit.getPlayer(uuid);
        if (this.player == null)
            throw new IllegalArgumentException("Player with UUID %s was not found, but is required for this constructor".formatted(uuid.toString()));
        this.permissionAttachmentInfos = this.player.getEffectivePermissions();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return this.player.hasPermission(permission);
    }

    @Override
    public boolean isOperator() {
        return this.player.isOp();
    }

    @Override
    public void sendMessage(@NotNull String message) {
        this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    @Override
    public boolean isPlayer() {
        return true;
    }
}
