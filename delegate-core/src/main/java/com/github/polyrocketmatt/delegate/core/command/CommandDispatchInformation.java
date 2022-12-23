package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.entity.CommanderEntity;

public class CommandDispatchInformation {

    private String name;
    private CommanderEntity commander;

    public CommandDispatchInformation(String name, CommanderEntity commander) {
        this.name = name;
        this.commander = commander;
    }
}
