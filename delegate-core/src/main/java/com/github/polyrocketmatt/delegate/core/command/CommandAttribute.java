package com.github.polyrocketmatt.delegate.core.command;

/**
 * Represents a command attribute that can be used to describe or modify
 * a command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public abstract class CommandAttribute {

    private final String identifier;

    /**
     * Creates a new command attribute with the given identifier.
     *
     * @param identifier The identifier of the attribute.
     */
    public CommandAttribute(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the identifier of the attribute.
     *
     * @return The identifier of the attribute.
     */
    public String getIdentifier() {
        return identifier;
    }
}
