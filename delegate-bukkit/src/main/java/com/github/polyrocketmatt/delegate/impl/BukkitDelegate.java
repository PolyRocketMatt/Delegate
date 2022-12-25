package com.github.polyrocketmatt.delegate.impl;

import com.github.polyrocketmatt.delegate.core.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.core.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.core.entity.ConsoleCommander;
import com.github.polyrocketmatt.delegate.core.entity.PlayerCommander;
import com.github.polyrocketmatt.delegate.core.platform.Platform;
import com.github.polyrocketmatt.delegate.core.platform.PlatformType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import static com.github.polyrocketmatt.delegate.core.Delegate.getDelegate;

public class BukkitDelegate extends JavaPlugin implements Platform {

    public static BukkitDelegate instance;

    public BukkitDelegate() {
        instance = this;

        //  Assign Bukkit as the platform
        getDelegate().setPlatform(this);
    }

    @Override
    public void onLoad() {
        getDelegate().setup();
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public File getFileStorage() {
        return getDataFolder();
    }

    @Override
    public String getPluginVersion() {
        return getDescription().getVersion();
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.BUKKIT;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return getDelegate()
                .getCommandHandler()
                .handle(new CommandDispatchInformation(getCommanderEntity(sender), command.getName()));
    }

    private CommanderEntity getCommanderEntity(CommandSender sender) {
        if (sender instanceof Player)
            return new PlayerCommander(((Player) sender).getUniqueId());
        else
            return new ConsoleCommander();
    }

}
