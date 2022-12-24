package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.properties.BrigadierProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.CommandProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandTree;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandTreeNode;

import java.util.List;

public class VerifiedDelegateCommand implements DelegateCommand {

    private final CommandTreeNode node;

    private final List<CommandArgument<?>> commandArguments;
    private boolean brigadierCompatible;
    private boolean ignoreNull;

    protected VerifiedDelegateCommand(CommandTreeNode parent, NameDefinition nameDefinition, List<CommandArgument<?>> commandArguments, List<CommandProperty> commandProperties) {
        this.node = new CommandTreeNode(parent, nameDefinition);
        this.commandArguments = commandArguments;
        this.brigadierCompatible = commandProperties.stream().anyMatch(property -> property instanceof BrigadierProperty);
        this.ignoreNull = commandProperties.stream().anyMatch(property -> property instanceof IgnoreNullProperty);

        if (brigadierCompatible)
            setupBrigadier();
    }

    private void setupBrigadier() {

    }

    @Override
    public CommandTreeNode getAsNode() {
        return this.node;
    }

    public boolean isBrigadierCompatible() {
        return brigadierCompatible;
    }

    public boolean isIgnoreNull() {
        return ignoreNull;
    }

    public static CommandBuilder create() {
        return new CommandBuilder();
    }

    public static class CommandBuilder {

        private CommandTreeNode parent;
        private NameDefinition nameDefinition;
        private List<CommandArgument<?>> commandArguments;
        private List<CommandProperty> commandProperties;

        public CommandBuilder buildParent(CommandTreeNode parent) {
            this.parent = parent;
            return this;
        }

        public CommandBuilder buildNameDefinition(NameDefinition nameDefinition) {
            this.nameDefinition = nameDefinition;
            return this;
        }

        public CommandBuilder buildArguments(List<CommandArgument<?>> commandArguments) {
            this.commandArguments = commandArguments;
            return this;
        }

        public CommandBuilder buildProperties(List<CommandProperty> commandProperties) {
            this.commandProperties = commandProperties;
            return this;
        }

        public VerifiedDelegateCommand build() {
            return new VerifiedDelegateCommand(
                    this.parent,
                    this.nameDefinition,
                    this.commandArguments,
                    this.commandProperties
            );
        }

    }

}
