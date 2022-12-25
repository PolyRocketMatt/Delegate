package com.github.polyrocketmatt.delegate.core.command.tree;

import java.util.ArrayList;
import java.util.List;

public class CommandTree {

    private final List<CommandNode> roots;

    public CommandTree() {
        this.roots = new ArrayList<>();
    }

    public List<CommandNode> getRoots() {
        return roots;
    }

    public void clear() {
        this.roots.clear();
    }

    public void add(CommandNode root) {
        this.roots.add(root);
    }

    public CommandNode find(String identifier) {
        for (CommandNode node : this.roots)
            if (node.getCommand().getNameDefinition().getValue().equalsIgnoreCase(identifier))
                return node;
        return null;
    }

}
