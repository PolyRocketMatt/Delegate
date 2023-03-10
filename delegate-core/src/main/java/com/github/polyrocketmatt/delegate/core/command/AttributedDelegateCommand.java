// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.definition.AliasDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.jetbrains.annotations.NotNull;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Implements the {@link DelegateCommand} interface and provides a class that
 * stores the name and description of the command together with the {@link DelegateCommandBuilder}
 * that is used to store the attributes of the command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class AttributedDelegateCommand extends DelegateCommand{

    private final NameDefinition nameDefinition;
    private final DescriptionDefinition descriptionDefinition;
    private final AliasDefinition[] aliases;
    private final DelegateCommandBuilder attributeChain;

    /**
     * Creates a new {@link AttributedDelegateCommand} with the given {@link DelegateCommandBuilder}.
     *
     * @param attributeChain The attribute chain that is used to store the attributes of the command.
     */
    public AttributedDelegateCommand(@NotNull DelegateCommandBuilder attributeChain) {
        validate("attributeChain", DelegateCommandBuilder.class, attributeChain);

        this.nameDefinition = (NameDefinition) attributeChain.find(NameDefinition.class);
        this.descriptionDefinition = (DescriptionDefinition) attributeChain.find(DescriptionDefinition.class);
        this.aliases = attributeChain.filter(AliasDefinition.class)
                .stream()
                .map(AliasDefinition.class::cast)
                .toArray(AliasDefinition[]::new);
        this.attributeChain = attributeChain;
    }

    /**
     * Gets the attribute chain that is used to store the attributes of the command.
     *
     * @return The attribute chain that is used to store the attributes of the command.
     */
    public @NotNull DelegateCommandBuilder getAttributeChain() {
        return attributeChain;
    }

    @Override
    public @NotNull NameDefinition getNameDefinition() {
        return nameDefinition;
    }

    @Override
    public @NotNull DescriptionDefinition getDescriptionDefinition() {
        return descriptionDefinition;
    }

    @Override
    public @NotNull AliasDefinition[] getAliases() {
        return aliases;
    }
}
