package com.github.polyrocketmatt.delegate.impl.entity;

import com.github.polyrocketmatt.delegate.api.entity.PlayerCommander;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.Set;
import java.util.UUID;

public class PaperPlayerCommander extends PlayerCommander {

    private final Player player;
    private final Set<PermissionAttachmentInfo> permissionAttachmentInfos;

    public PaperPlayerCommander(Player player) {
        super(player.getUniqueId());
        this.player = player;
        this.permissionAttachmentInfos = player.getEffectivePermissions();
    }

    @SuppressWarnings("")
    public PaperPlayerCommander(UUID uuid) {
        super(uuid);
        this.player = Bukkit.getPlayer(uuid);
        if (this.player == null)
            throw new IllegalArgumentException("Player with UUID %s was not found, but is required for this constructor".formatted(uuid.toString()));
        this.permissionAttachmentInfos = this.player.getEffectivePermissions();
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.player.hasPermission(permission);
    }

    @Override
    public boolean isOperator() {
        return this.player.isOp();
    }

    @Override
    public void sendMessage(String message) {
        this.player.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', message)));
    }
}
