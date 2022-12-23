package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;

public class AttributedDelegateCommand implements DelegateCommand{

    private final CommandPath path;
    private final CommandAttributeChain attributeChain;

    public AttributedDelegateCommand(CommandAttributeChain attributeChain) {
        this.path = new CommandPath(null, (NameDefinition) attributeChain.find(NameDefinition.class));
        this.attributeChain = attributeChain;
    }

    public AttributedDelegateCommand(NameDefinition parent, CommandAttributeChain attributeChain) {
        this.path = new CommandPath(parent, (NameDefinition) attributeChain.find(NameDefinition.class));
        this.attributeChain = attributeChain;
    }

    public CommandAttributeChain getAttributeChain() {
        return attributeChain;
    }

    @Override
    public CommandPath getPath() {
        return path;
    }
}
