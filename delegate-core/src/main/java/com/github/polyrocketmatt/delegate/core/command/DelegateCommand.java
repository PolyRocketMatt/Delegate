package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;

/**
 * Defines a command that has a name and description.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public interface DelegateCommand {

    /**
     * Gets the {@link NameDefinition} of this command.
     *
     * @return The name definition of this command.
     */
    NameDefinition getNameDefinition();

    /**
     * Gets the {@link DescriptionDefinition} of this command.
     *
     * @return The description definition of this command.
     */
    DescriptionDefinition getDescriptionDefinition();

}
