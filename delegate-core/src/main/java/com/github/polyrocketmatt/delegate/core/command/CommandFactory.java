package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;

/**
 * Represents a factory for creating commands.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class CommandFactory {

    @Deprecated
    public static DelegateCommandBuilder create() {
        return new DelegateCommandBuilder();
    }

    @Deprecated
    public static DelegateCommandBuilder create(String name) {
        return new DelegateCommandBuilder().with(new NameDefinition(name));
    }

    /**
     * Creates a new {@link DelegateCommandBuilder} with the given name and description.
     *
     * @param name The name of the command.
     * @param description The description of the command.
     * @return The new {@link DelegateCommandBuilder}.
     */
    public static DelegateCommandBuilder create(String name, String description) {
        return new DelegateCommandBuilder().with(new NameDefinition(name)).with(new DescriptionDefinition(description));
    }

}
