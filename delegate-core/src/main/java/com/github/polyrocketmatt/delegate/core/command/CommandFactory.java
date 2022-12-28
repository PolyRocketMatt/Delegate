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
    public static CommandAttributeChain create() {
        return new CommandAttributeChain();
    }

    @Deprecated
    public static CommandAttributeChain create(String name) {
        return new CommandAttributeChain().append(new NameDefinition(name));
    }

    /**
     * Creates a new {@link CommandAttributeChain} with the given name and description.
     *
     * @param name The name of the command.
     * @param description The description of the command.
     * @return The new {@link CommandAttributeChain}.
     */
    public static CommandAttributeChain create(String name, String description) {
        return new CommandAttributeChain().append(new NameDefinition(name)).append(new DescriptionDefinition(description));
    }

}
