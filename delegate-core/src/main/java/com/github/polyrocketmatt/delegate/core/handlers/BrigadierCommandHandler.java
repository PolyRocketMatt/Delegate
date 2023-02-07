package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.api.IHandler;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;

public class BrigadierCommandHandler implements IHandler {

    /**
     * Adds a {@link CommandNode} to the brigadier structure.
     *
     * @param node The {@link CommandNode} to add.
     */
    public boolean registerCommand(CommandNode node) {
        return true;
    }

}
