package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.core.handlers.AttributeHandler;
import com.github.polyrocketmatt.delegate.core.handlers.CommandHandler;
import com.github.polyrocketmatt.delegate.core.platform.Platform;

import java.io.File;

public class Delegate {

    private static final Delegate instance = new Delegate();

    private Platform platform;
    private final AttributeHandler attributeHandler;
    private final CommandHandler commandHandler;

    protected Delegate() {
        if (instance != null)
            throw new IllegalStateException("Delegate has already been initialized");
        this.attributeHandler = new AttributeHandler();
        this.commandHandler = new CommandHandler();
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

    public AttributeHandler getAttributeHandler() {
        return attributeHandler;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
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
    }

}
