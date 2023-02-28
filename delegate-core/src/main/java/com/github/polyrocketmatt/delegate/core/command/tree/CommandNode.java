// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.tree;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.api.command.tree.ICommandNode;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Represents a node in the command tree. It contains a parent node,
 * a list of child nodes and the command to execute at this depth in the tree.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class CommandNode implements ICommandNode {

    private CommandNode parent;
    private final List<CommandNode> children;
    private DelegateCommand command;

    /**
     * Creates a new {@link CommandNode} with a command. It will have no parent
     * and no children.
     *
     * @param command The command to execute at this depth in the tree.
     */
    public CommandNode(@NotNull DelegateCommand command) {
        validate("command", DelegateCommand.class, command);

        this.parent = null;
        this.children = new ArrayList<>();
        this.command = command;
    }

    /**
     * Creates a new {@link CommandNode} with a command and a parent. It will have
     * no children.
     *
     * @param parent The parent node.
     * @param command The command to execute at this depth in the tree.
     */
    public CommandNode(@NotNull CommandNode parent, @NotNull DelegateCommand command) {
        validate("parent", CommandNode.class, parent);
        validate("command", DelegateCommand.class, command);

        this.parent = parent;
        this.children = new ArrayList<>();
        this.command = command;

        //  If the parent doesn't have this node as a child, add it.
        if (!parent.getChildren().contains(this))
            parent.getChildren().add(this);
    }

    /**
     * Gets the parent node.
     *
     * @return The parent node.
     */
    public @Nullable CommandNode getParent() {
        return parent;
    }

    /**
     * Gets the command to execute at this depth in the tree.
     *
     * @return The command to execute at this depth in the tree.
     */
    public @NotNull DelegateCommand getCommand() {
        return command;
    }

    public void setCommand(@NotNull DelegateCommand command) {
        validate("command", DelegateCommand.class, command);

        this.command = command;
    }

    /**
     * Gets the list of child nodes.
     *
     * @return The list of child nodes.
     */
    @SuppressWarnings("unchecked")
    @Override
    public @NotNull List<CommandNode> getChildren() {
        return children;
    }

    /**
     * Adds a child node to this node.
     *
     * @param child The child node to add.
     */
    public void addChild(@NotNull CommandNode child) {
        validate("child", CommandNode.class, child);

        //  Child must have this node as a parent
        if (child.getParent() != this)
            setParent(this);

        //  If the child already exists, don't add it again
        if (this.children.contains(child))
            throw new IllegalArgumentException("Child node already exists");
        this.children.add(child);
    }

    private void setParent(@NotNull CommandNode parent) {
        this.parent = parent;
    }

    /**
     * Checks if the command at this depth in the tree is verified, meaning
     * that it is a {@link VerifiedDelegateCommand}.
     *
     * @return True if the command is verified, false otherwise.
     */
    public boolean isVerified() {
        return command instanceof VerifiedDelegateCommand;
    }

    /**
     * Uses breadth-first search to find a child node that has the same name
     * as the first argument in the array of given names. If a child node has
     * been found, it will be explored recursively until all names have been
     * checked. If a child node has not been found, this node will be returned.
     * <p>
     * The result is a {@link QueryResultNode} that contains the node that was
     * found and an array of remaining names that are inferred to be arguments.
     *
     * @param commandPattern The pattern of the currently matched command.
     * @param names The array of names to search for.
     * @return The {@link QueryResultNode} that contains the node that was found
     */
    public @NotNull QueryResultNode findDeepest(@NotNull String commandPattern, @NotNull String[] names) {
        validate("commandPattern", String.class, commandPattern);
        validate("names", String[].class, names);
        for (String name : names)
            validate("name", String.class, name);

        if (this.children.isEmpty() || names.length == 0)
            return new QueryResultNode(this, commandPattern, names);

        String name = names[0];
        String[] remaining = new String[names.length - 1];
        System.arraycopy(names, 1, remaining, 0, remaining.length);
        CommandNode child = this.children.stream()
                .filter(c -> c.getCommand().getNameDefinition().getValue().equalsIgnoreCase(name) ||
                        Arrays.stream(c.getCommand().getAliases()).anyMatch(alias -> alias.getValue().equalsIgnoreCase(name)))
                .findFirst()
                .orElse(null);
        return child == null ? new QueryResultNode(this, commandPattern, names) :
                child.findDeepest(commandPattern + " " + name, remaining);
    }

    @Override
    public @NotNull CommandDefinition<String> getNameDefinition() {
        return this.command.getNameDefinition();
    }

    @Override
    public @NotNull CommandDefinition<String> getDescriptionDefinition() {
        return this.command.getDescriptionDefinition();
    }

    @Override
    public @NotNull CommandDefinition<String>[] getAliasDefinitions() {
        return this.command.getAliases();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof CommandNode node)) return false;

        //  We assume equality if the commands are equal
        return this.command.equals(node.getCommand());
    }
}
