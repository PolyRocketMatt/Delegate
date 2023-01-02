package com.github.polyrocketmatt.delegate.impl.event;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.event.CommandEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DelegateCommandEvent extends Event implements CommandEvent {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final CommandDispatchInformation dispatchInformation;
    private final CommandCapture capture;

    public DelegateCommandEvent(CommandDispatchInformation dispatchInformation, CommandCapture capture) {
        this.dispatchInformation = dispatchInformation;
        this.capture = capture;
    }

    @Override
    public CommandDispatchInformation getDispatchInformation() {
        return dispatchInformation;
    }

    public CommandCapture getCapture() {
        return capture;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
