package com.github.polyrocketmatt.delegate.core.command.tree;

import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;

import java.util.ArrayList;
import java.util.List;

public class CommandTreeNode {

    private final CommandTreeNode parent;
    private final NameDefinition nameDefinition;
    private final List<CommandTreeNode> children;

    public CommandTreeNode(CommandTreeNode parent, NameDefinition nameDefinition) {
        this.parent = parent;
        this.nameDefinition = nameDefinition;
        this.children = new ArrayList<>();
    }

    public CommandTreeNode getParent() {
        return parent;
    }

    public NameDefinition getNameDefinition() {
        return nameDefinition;
    }

    public List<CommandTreeNode> getChildren() {
        return children;
    }

    public void addChild(CommandTreeNode child) {
        this.children.add(child);
    }

    public int depth() {
        return this.parent == null ? 0 : this.parent.depth() + 1;
    }
}
