package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.core.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandTree;

public class CommandHandler implements Handler {

    private final CommandTree commandTree;

    public CommandHandler() {
        this.commandTree = new CommandTree();
    }

    public void registerTree(CommandNode root) {
        this.commandTree.add(root);
    }

    public boolean handle(CommandDispatchInformation information) {
        CommandNode root = this.commandTree.find(information.command());

        //  Parse information arguments until command in root node doesn't exist
        //  If command in root node doesn't exist, then we have found the command
        //  We can then parse the remaining arguments, apply rules to them and parse them.
        //  We can execute the command, which should be a verified command since it resides in a command node.
        //  Finally, we parse argument rules, resolve possible contexts and return proper results.

        return true;
    }

    public boolean dispatch(VerifiedDelegateCommand command, CommandDispatchInformation information) {
        return true;
    }

    @Override
    public void init() {}

    @Override
    public void destroy() {
        this.commandTree.clear();
    }

}
