// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.tree;

import com.github.polyrocketmatt.delegate.api.command.tree.ICommandTree;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

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
    public void add(@NotNull CommandNode root) {
        validate("root", CommandNode.class, root);

        //  If the root already exists, throw an exception.
        if (this.roots.contains(root))
            throw new IllegalArgumentException("The root already exists in the tree");

        this.roots.add(root);
    }

    /**
     * Finds a root with the given identifier.
     *
     * @param identifier The identifier to search for.
     * @return The root with the given identifier, or null if none was found.
     */
    public @Nullable CommandNode find(@NotNull String identifier) {
        validate("identifier", String.class, identifier);

        for (CommandNode node : this.roots)
            if (node.getCommand().getNameDefinition().getValue().equalsIgnoreCase(identifier))
                return node;
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull List<CommandNode> getRoots() {
        return roots;
    }

    @Override
    public int size() {
        return roots.size() + roots.stream().mapToInt(CommandNode::size).sum();
    }
}
