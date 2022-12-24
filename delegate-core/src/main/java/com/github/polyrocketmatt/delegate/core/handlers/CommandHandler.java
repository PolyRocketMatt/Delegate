package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.core.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandTree;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements Handler {

    private final List<CommandTree> trees;

    public CommandHandler() {
        this.trees = new ArrayList<>();
    }

    public List<CommandTree> getTrees() {
        return trees;
    }

    public void registerTree(CommandTree tree) {
        this.trees.add(tree);
    }

    public boolean handle(CommandDispatchInformation information) {
        //  TODO: Handle command dispatch
        return true;
    }

    @Override
    public void init() {}

    @Override
    public void destroy() {
        this.trees.clear();
    }

}
