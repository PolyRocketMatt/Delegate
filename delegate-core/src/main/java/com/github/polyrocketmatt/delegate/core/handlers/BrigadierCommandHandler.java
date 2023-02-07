package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.api.IHandler;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class BrigadierCommandHandler implements IHandler {

    private final CommandDispatcher<CommanderEntity> dispatcher;

    public BrigadierCommandHandler() {
        this.dispatcher = new CommandDispatcher<>();
    }

    /**
     * Adds a {@link CommandNode} to the brigadier structure.
     *
     * @param node The {@link CommandNode} to add.
     */
    public boolean registerCommand(CommandNode node) {
        return true;
    }

    /**
     * Handles the given {@link CommandDispatchInformation} and tries to execute the
     * command associated with the information.
     *
     * @param information The {@link CommandDispatchInformation} to handle.
     * @return True if the information was handled successfully, false otherwise.
     * @throws CommandExecutionException If an error occurred while parsing the information.
     */
    public boolean handle(CommandDispatchInformation information) throws CommandExecutionException, CommandSyntaxException {
        String command = information.command() + " " + String.join(" ", information.arguments());
        CommanderEntity commander = information.commander();

        //  Execution is now handled by Brigadier
        return dispatcher.execute(command, commander) > 0;
    }

}
