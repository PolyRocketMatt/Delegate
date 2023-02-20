// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.impl.event;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.PlayerCommander;
import com.github.polyrocketmatt.delegate.api.event.CommandEvent;
import com.github.polyrocketmatt.delegate.api.exception.CommandEventException;
import com.github.polyrocketmatt.delegate.impl.Delegate;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DelegateCommandEvent implements CommandEvent {

    private final Class<?> plugin;
    private final CommandDispatchInformation dispatchInformation;
    private final CommandCapture capture;
    private final Player sender;

    public DelegateCommandEvent(Class<?> plugin, CommandDispatchInformation dispatchInformation, CommandCapture capture) {
        //  Check if plugin contains the @Plugin notation from Velocity
        if (!plugin.isAnnotationPresent(com.velocitypowered.api.plugin.Plugin.class))
            throw new CommandEventException("Plugin must be annotated with @Plugin");

        this.plugin = plugin;
        this.dispatchInformation = dispatchInformation;
        this.capture = capture;
        if (dispatchInformation.commander() instanceof PlayerCommander) {
            Optional<Player> optionalPlayer = Delegate.getProxy().getPlayer((((PlayerCommander) dispatchInformation.commander()).getUniqueId()));
            this.sender = optionalPlayer.orElse(null);
        } else {
            this.sender = null;
        }

    }

    public Class<?> getPlugin() {
        return plugin;
    }

    @Override
    public @NotNull CommandDispatchInformation getDispatchInformation() {
        return dispatchInformation;
    }

    @Override
    public @NotNull CommandCapture getCapture() {
        return capture;
    }

    public CommandSource getSender() {
        return (this.sender == null) ? Delegate.getProxy().getConsoleCommandSource() : this.sender;
    }

    public String getCommand() {
        return dispatchInformation.command();
    }

    public String[] getArguments() {
        return dispatchInformation.arguments();
    }
}
