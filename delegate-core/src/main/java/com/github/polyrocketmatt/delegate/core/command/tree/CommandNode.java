package com.github.polyrocketmatt.delegate.core.command.tree;

import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandNode {

    private final CommandNode parent;
    private final DelegateCommand command;
    private final List<CommandNode> children;

    public CommandNode(DelegateCommand command) {
        this.parent = null;
        this.command = command;
        this.children = new ArrayList<>();
    }

    public CommandNode(CommandNode parent, DelegateCommand command) {
        this.parent = parent;
        this.command = command;
        this.children = new ArrayList<>();
    }

    public CommandNode getParent() {
        return parent;
    }

    public DelegateCommand getCommand() {
        return command;
    }

    public List<CommandNode> getChildren() {
        return children;
    }

    public void addChild(CommandNode child) {
        this.children.add(child);
    }

    public boolean isVerified() {
        return command instanceof VerifiedDelegateCommand;
    }

}
