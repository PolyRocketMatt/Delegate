// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.api.command.IDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.definition.AliasDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * Defines a command that has a name and description.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public abstract class DelegateCommand implements IDelegateCommand {

    /**
     * Gets the {@link NameDefinition} of this command.
     *
     * @return The name definition of this command.
     */
    public abstract @NotNull NameDefinition getNameDefinition();

    /**
     * Gets the {@link DescriptionDefinition} of this command.
     *
     * @return The description definition of this command.
     */
    public abstract @NotNull DescriptionDefinition getDescriptionDefinition();

    /**
     * Get the {@link AliasDefinition}s of this command.
     *
     * @return The alias definitions of this command.
     */
    public abstract AliasDefinition[] getAliases();

}
