package com.github.polyrocketmatt.delegate.core.command.tree;

public class CommandTree {

    private final CommandNode root;

    public CommandTree(CommandNode root) {
        this.root = root;
    }

    public CommandNode getRoot() {
        return root;
    }
}
