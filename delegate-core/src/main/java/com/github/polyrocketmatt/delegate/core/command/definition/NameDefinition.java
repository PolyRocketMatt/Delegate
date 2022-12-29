package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;

/**
 * Represents a {@link CommandDefinition} that describes the name
 * of the command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class NameDefinition extends CommandDefinition<String> {

    /**
     * Creates a new {@link NameDefinition} with a name.
     *
     * @param name The name of the command.
     */
    public NameDefinition(String name) {
        super("commandName", name);
    }

}
