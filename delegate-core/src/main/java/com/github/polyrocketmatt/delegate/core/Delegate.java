package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.core.handlers.CommandHandler;
import com.github.polyrocketmatt.delegate.core.handlers.PluginHandler;
import com.github.polyrocketmatt.delegate.core.platform.Platform;

import java.io.File;

import static com.github.polyrocketmatt.delegate.core.io.FileHandler.PLUGIN_CONFIG_PATH;

public class Delegate {

    private static final Delegate instance = new Delegate();

    private Platform platform;
    private final CommandHandler commandHandler;
    private final PluginHandler pluginHandler;

    protected Delegate() {
        if (instance != null)
            throw new IllegalStateException("Delegate has already been initialized");
        this.commandHandler = new CommandHandler();
        this.pluginHandler = new PluginHandler();
    }

    public static Delegate getDelegate() {
        return instance;
    }

    public void setPlatform(Platform platform) {
        if (this.platform != null)
            throw new IllegalStateException("Owner has already been set");
        this.platform = platform;
    }

    public Platform getPlatform() {
        return platform;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public PluginHandler getPluginHandler() {
        return pluginHandler;
    }

    public File dataFolder() {
        return platform.getFileStorage();
    }

    public String getDelegateVersion() {
        return this.platform.getPluginVersion();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void setup() {
        if (!dataFolder().exists())
            dataFolder().mkdirs();

        File configFolder = new File(dataFolder(), PLUGIN_CONFIG_PATH);

        if (!configFolder.exists())
            configFolder.mkdirs();
    }

}
