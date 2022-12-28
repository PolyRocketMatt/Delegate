package com.github.polyrocketmatt.delegate.impl;

import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.platform.Platform;
import com.github.polyrocketmatt.delegate.core.platform.PlatformType;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import java.io.File;
import java.util.logging.Logger;

import static com.github.polyrocketmatt.delegate.core.DelegateFramework.getDelegate;

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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Inject
    public VelocityDelegate(ProxyServer server, Logger logger) {
        this.server = server;
        this.dataFolder = new File("%s/%s".formatted(System.getProperty("user.dir"), "/plugins/delegate/"));
        if (!dataFolder.exists())
            dataFolder.mkdirs();

        getDelegate().setPlatform(this);
    }

    @Override
    public String getPluginVersion() {
        return "0.0.1";
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.VELOCITY;
    }

    @Override
    public boolean hasPermission(String permission) throws UnsupportedOperationException {
        return false;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

    }

    public void registerVelocityCommand(VerifiedDelegateCommand command) {

    }

}
