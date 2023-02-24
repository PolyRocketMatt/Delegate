// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.impl;

import com.github.polyrocketmatt.delegate.api.DelegateAPI;
import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.PlatformType;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.api.command.IDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.core.DelegateCore;
import com.github.polyrocketmatt.delegate.impl.command.VelocityCommandBuilder;
import com.github.polyrocketmatt.delegate.impl.command.VelocityCommandFactory;
import com.github.polyrocketmatt.delegate.impl.entity.VelocityPlayerCommander;
import com.github.polyrocketmatt.delegate.impl.event.DelegateCommandEvent;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.proxy.ProxyServer;
import org.bstats.velocity.Metrics;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;

public class Delegate implements IPlatform {

    private static final int VELOCITY_DELEGATE_ID = 17672;

    private static final VelocityCommandFactory factory = new VelocityCommandFactory();
    private static ProxyServer proxy;

    private final Class<?> plugin;
    private final CommandManager commandManager;
    private final List<IDelegateCommand> commands = new ArrayList<>();
    //private final InternalCommandHandler commandHandler;
    private final boolean metricsEnabled;

    protected Delegate(Class<?> plugin, ProxyServer server, boolean metricsEnabled, Metrics.Factory metricsFactory) {
        proxy = server;

        this.plugin = plugin;
        this.commandManager = proxy.getCommandManager();
        //this.commandHandler = getDelegate().getCommandHandler();
        this.metricsEnabled = metricsEnabled;
        if (metricsEnabled)
            metricsFactory.make(plugin, VELOCITY_DELEGATE_ID);
    }

    public static ProxyServer getProxy() {
        return proxy;
    }

    public static void hook(Class<?> plugin, ProxyServer proxy, Metrics.Factory factory) {
        hook(plugin, proxy, true, factory, true);
    }

    public static void hook(Class<?> plugin, ProxyServer proxy, boolean enableMetrics, Metrics.Factory factory, boolean verbose) {
        getDelegate().setPlatform(new Delegate(plugin, proxy, enableMetrics, factory));
        getDelegate().setVerbose(verbose);
    }

    public static void unhook(Class<?> plugin) {
        Delegate delegate = (Delegate) getDelegate().getPlatform();
        delegate.unregister();

        getDelegate().setPlatform(null);
    }

    public static DelegateAPI getDelegateAPI() {
        return DelegateCore.getDelegateAPI();
    }

    public static VelocityCommandBuilder create(@NotNull String name, @NotNull String description) {
        return factory.create(name, description);
    }

    private Class<?> getPlugin() {
        return plugin;
    }

    @Override
    public @NotNull PlatformType getPlatformType() {
        return PlatformType.PAPER;
    }

    @Override
    public @NotNull ICommandFactory getFactoryImplementation() {
        return factory;
    }

    @Override
    public void registerToPlatform(@NotNull IDelegateCommand command) throws CommandRegisterException {
        //  TODO: Implement Brigadier support

    }

    @Override
    public boolean execute(@NotNull CommandDispatchInformation information) throws CommandExecutionException {
        return getDelegateAPI().getCommandHandler().handle(information);
    }

    private void unregister() throws CommandRegisterException {
        commands.clear();
    }

    @Override
    public boolean hasPermission(@NotNull CommanderEntity entity, @NotNull String permission) throws UnsupportedOperationException {
        if (!(entity instanceof VelocityPlayerCommander commander))
            throw new UnsupportedOperationException("Expected entity to be of type BukkitPlayerCommander, but got %s".formatted(entity.getClass().getName()));
        return commander.hasPermission(permission);
    }

    @Override
    public boolean isOperator(@NotNull CommanderEntity entity) throws UnsupportedOperationException {
        if (!(entity instanceof VelocityPlayerCommander commander))
            throw new UnsupportedOperationException("Expected entity to be of type BukkitPlayerCommander, but got %s".formatted(entity.getClass().getName()));
        return commander.isOperator();
    }

    @Override
    public boolean dispatch(@NotNull CommandDispatchInformation information, @NotNull CommandCapture capture) {
        DelegateCommandEvent event = new DelegateCommandEvent(plugin, information, capture);
        proxy.getEventManager().fire(event);

        return true;
    }

    @Override
    public boolean metricsEnabled() {
        return metricsEnabled;
    }
}
