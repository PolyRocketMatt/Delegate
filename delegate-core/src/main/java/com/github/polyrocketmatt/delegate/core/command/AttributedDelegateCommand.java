package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;

/**
 * Implements the {@link DelegateCommand} interface and provides a class that
 * stores the name and description of the command together with the {@link CommandAttributeChain}
 * that is used to store the attributes of the command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class AttributedDelegateCommand implements DelegateCommand{

    private final NameDefinition nameDefinition;
    private final DescriptionDefinition descriptionDefinition;
    private final CommandAttributeChain attributeChain;

    /**
     * Creates a new {@link AttributedDelegateCommand} with the given {@link CommandAttributeChain}.
     *
     * @param attributeChain The attribute chain that is used to store the attributes of the command.
     */
    public AttributedDelegateCommand(CommandAttributeChain attributeChain) {
        this.nameDefinition = (NameDefinition) attributeChain.find(NameDefinition.class);
        this.descriptionDefinition = (DescriptionDefinition) attributeChain.find(DescriptionDefinition.class);
        this.attributeChain = attributeChain;
    }

    /**
     * Gets the attribute chain that is used to store the attributes of the command.
     *
     * @return The attribute chain that is used to store the attributes of the command.
     */
    public CommandAttributeChain getAttributeChain() {
        return attributeChain;
    }

    @Override
    public NameDefinition getNameDefinition() {
        return nameDefinition;
    }

    @Override
    public DescriptionDefinition getDescriptionDefinition() {
        return descriptionDefinition;
    }
}
