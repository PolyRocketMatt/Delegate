package com.github.polyrocketmatt.delegate.impl;

import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.PlatformType;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import java.io.File;
import java.util.logging.Logger;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;

@Plugin(
        id = "delegate",
        name = "Delegate",
        version = "0.0.1",
        description = "Delegate API for Velocity",
        authors = {
                "Matthias Kovacic"
        }
)
public class VelocityDelegate implements IPlatform {

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
    public PlatformType getPlatformType() {
        return PlatformType.VELOCITY;
    }

    @Override
    public boolean hasPermission(CommanderEntity entity, String permission) throws UnsupportedOperationException {
        return false;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

    }

    public void registerVelocityCommand(VerifiedDelegateCommand command) {

    }

}
