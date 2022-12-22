package com.github.polyrocketmatt.delegate.impl;

import com.github.polyrocketmatt.delegate.core.platform.Platform;
import com.github.polyrocketmatt.delegate.core.platform.PlatformType;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import java.io.File;
import java.util.logging.Logger;

import static com.github.polyrocketmatt.delegate.core.Delegate.getDelegate;

@Plugin(
        id = "delegate",
        name = "Delegate",
        version = "0.0.1",
        description = "Delegate API for Velocity",
        authors = {
                "Matthias Kovacic"
        }
)
public class VelocityDelegate implements Platform {

    private final File dataFolder;
    private final ProxyServer server;
    private final Logger logger;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Inject
    public VelocityDelegate(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        this.logger.info("Loaded Delegate");
        this.logger.info("Platform: %s".formatted(getPlatformType().name()));

        this.dataFolder = new File("%s/%s".formatted(System.getProperty("user.dir"), "/plugins/delegate/"));
        if (!dataFolder.exists())
            dataFolder.mkdirs();

        getDelegate().setPlatform(this);
    }

    @Override
    public File getFileStorage() {
        return this.dataFolder;
    }

    @Override
    public String getPluginVersion() {
        return "0.0.1";
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.VELOCITY;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.logger.info("Delegate: Proxy initialization fired");

        getDelegate().setup();
        getDelegate().getPluginHandler().registerPlugin("Delegate");
    }
}
