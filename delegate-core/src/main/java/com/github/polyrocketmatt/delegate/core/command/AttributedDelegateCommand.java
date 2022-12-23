package com.github.polyrocketmatt.delegate.core.command;

public class AttributedDelegateCommand implements DelegateCommand{

    private final CommandAttributeChain attributeChain;

    public AttributedDelegateCommand(CommandAttributeChain attributeChain) {
        this.attributeChain = new CommandAttributeChain();
    }

    public CommandAttributeChain getAttributeChain() {
        return attributeChain;
    }
}
