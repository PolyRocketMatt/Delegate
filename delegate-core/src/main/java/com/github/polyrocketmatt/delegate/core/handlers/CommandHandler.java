package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.core.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements Handler {

    private final List<VerifiedDelegateCommand> commands;

    public CommandHandler() {
        this.commands = new ArrayList<>();
    }

    public void register(VerifiedDelegateCommand command) {
        commands.add(command);
    }

    public boolean handle(CommandDispatchInformation information) {
        //  TODO: Handle command dispatch
        return true;
    }

    @Override
    public void init() {}

    @Override
    public void destroy() {
        this.commands.clear();
    }

}
