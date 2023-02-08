// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.tree;

import com.github.polyrocketmatt.delegate.api.command.tree.ICommandTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the root of multiple possible {@link CommandNode}s as roots.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class CommandTree implements ICommandTree {

    private final List<CommandNode> roots;

    /**
     * Creates a new {@link CommandTree} with no roots.
     */
    public CommandTree() {
        this.roots = new ArrayList<>();
    }

    /**
     * Clears the list of roots.
     */
    public void clear() {
        this.roots.clear();
    }

    /**
     * Adds a root to the list of roots.
     *
     * @param root The root to add.
     */
    public void add(CommandNode root) {
        this.roots.add(root);
    }

    /**
     * Finds a root with the given identifier.
     *
     * @param identifier The identifier to search for.
     * @return The root with the given identifier, or null if none was found.
     */
    public CommandNode find(String identifier) {
        for (CommandNode node : this.roots)
            if (node.getCommand().getNameDefinition().getValue().equalsIgnoreCase(identifier))
                return node;
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CommandNode> getRoots() {
        return roots;
    }
}
