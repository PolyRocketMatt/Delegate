package com.github.polyrocketmatt.delegate.impl.event;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.PlayerCommander;
import com.github.polyrocketmatt.delegate.api.event.CommandEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DelegateCommandEvent extends Event implements CommandEvent {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final CommandDispatchInformation dispatchInformation;
    private final CommandCapture capture;
    private final Player sender;

    public DelegateCommandEvent(CommandDispatchInformation dispatchInformation, CommandCapture capture) {
        this.dispatchInformation = dispatchInformation;
        this.capture = capture;
        if (dispatchInformation.commander() instanceof PlayerCommander) {
            this.sender = Bukkit.getPlayer((((PlayerCommander) dispatchInformation.commander()).getUniqueId()));
        } else {
            this.sender = null;
        }
    }

    @Override
    public CommandDispatchInformation getDispatchInformation() {
        return dispatchInformation;
    }

    public CommandCapture getCapture() {
        return capture;
    }

    public CommandSender getSender() {
        return (this.sender == null) ? Bukkit.getConsoleSender() : (CommandSender) this.sender;
    }

    public String getCommand() {
        return dispatchInformation.command();
    }

    public String[] getArguments() {
        return dispatchInformation.arguments();
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
