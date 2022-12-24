package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;

public class AttributedDelegateCommand implements DelegateCommand{

    private final NameDefinition nameDefinition;
    private final CommandAttributeChain attributeChain;

    public AttributedDelegateCommand(CommandAttributeChain attributeChain) {
        this.nameDefinition = (NameDefinition) attributeChain.find(NameDefinition.class);
        this.attributeChain = attributeChain;
    }

    public CommandAttributeChain getAttributeChain() {
        return attributeChain;
    }

    @Override
    public NameDefinition getNameDefinition() {
        return nameDefinition;
    }
}
