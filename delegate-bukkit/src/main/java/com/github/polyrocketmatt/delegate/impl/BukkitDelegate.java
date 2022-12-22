package com.github.polyrocketmatt.delegate.impl;

import com.github.polyrocketmatt.delegate.core.platform.Platform;
import com.github.polyrocketmatt.delegate.core.platform.PlatformType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static com.github.polyrocketmatt.delegate.core.Delegate.getDelegate;
import static com.github.polyrocketmatt.delegate.core.io.FileHandler.PLUGIN_CONFIG_PATH;

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
        getDelegate().getPluginHandler().registerPlugin(getDescription().getName());
    }

    @Override
    public void onDisable() {
        getDelegate().getPluginHandler().unregisterPlugin(getDescription().getName());
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

}
