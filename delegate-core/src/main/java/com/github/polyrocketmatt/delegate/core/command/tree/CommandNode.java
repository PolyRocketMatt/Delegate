package com.github.polyrocketmatt.delegate.core.command.tree;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.api.command.tree.ICommandNode;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in the command tree. It contains a parent node,
 * a list of child nodes and the command to execute at this depth in the tree.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class CommandNode implements ICommandNode {

    private final CommandNode parent;
    private final DelegateCommand command;
    private final List<CommandNode> children;

    /**
     * Creates a new {@link CommandNode} with a command. It will have no parent
     * and no children.
     *
     * @param command The command to execute at this depth in the tree.
     */
    public CommandNode(DelegateCommand command) {
        this.parent = null;
        this.command = command;
        this.children = new ArrayList<>();
    }

    /**
     * Creates a new {@link CommandNode} with a command and a parent. It will have
     * no children.
     *
     * @param parent The parent node.
     * @param command The command to execute at this depth in the tree.
     */
    public CommandNode(CommandNode parent, DelegateCommand command) {
        this.parent = parent;
        this.command = command;
        this.children = new ArrayList<>();
    }

    /**
     * Gets the parent node.
     *
     * @return The parent node.
     */
    public CommandNode getParent() {
        return parent;
    }

    /**
     * Gets the command to execute at this depth in the tree.
     *
     * @return The command to execute at this depth in the tree.
     */
    public DelegateCommand getCommand() {
        return command;
    }

    /**
     * Gets the list of child nodes.
     *
     * @return The list of child nodes.
     */
    public List<CommandNode> getChildren() {
        return children;
    }

    /**
     * Adds a child node to this node.
     *
     * @param child The child node to add.
     */
    public void addChild(CommandNode child) {
        this.children.add(child);
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
     * @param names The array of names to search for.
     * @return The {@link QueryResultNode} that contains the node that was found
     */
    public QueryResultNode findDeepest(String[] names) {
        if (this.children.isEmpty() || names.length == 0)
            return new QueryResultNode(this, names);

        String name = names[0];
        String[] remaining = new String[names.length - 1];
        System.arraycopy(names, 1, remaining, 0, remaining.length);
        CommandNode child = this.children.stream()
                .filter(c -> c.getCommand().getNameDefinition().getValue().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
        return child == null ? new QueryResultNode(this, names) : child.findDeepest(remaining);
    }

    @Override
    public CommandDefinition<String> getNameDefinition() {
        return command.getNameDefinition();
    }

    @Override
    public CommandDefinition<String> getDescriptionDefinition() {
        return command.getDescriptionDefinition();
    }
}
