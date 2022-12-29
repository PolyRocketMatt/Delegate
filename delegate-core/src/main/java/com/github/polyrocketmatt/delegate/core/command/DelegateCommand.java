package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.api.command.IDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;

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
    public abstract NameDefinition getNameDefinition();

    /**
     * Gets the {@link DescriptionDefinition} of this command.
     *
     * @return The description definition of this command.
     */
    public abstract DescriptionDefinition getDescriptionDefinition();

}
