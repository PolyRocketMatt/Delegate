package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandTree;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandTreeNode;

public class AttributedDelegateCommand implements DelegateCommand{

    private final CommandTreeNode node;
    private final CommandAttributeChain attributeChain;

    public AttributedDelegateCommand(CommandAttributeChain attributeChain) {
        this.node = new CommandTreeNode(null, (NameDefinition) attributeChain.find(NameDefinition.class));
        this.attributeChain = attributeChain;
    }

    public AttributedDelegateCommand(CommandTreeNode parent, CommandAttributeChain attributeChain) {
        this.node = new CommandTreeNode(parent, (NameDefinition) attributeChain.find(NameDefinition.class));
        this.attributeChain = attributeChain;
    }

    public CommandAttributeChain getAttributeChain() {
        return attributeChain;
    }

    @Override
    public CommandTreeNode getAsNode() {
        return this.node;
    }
}
