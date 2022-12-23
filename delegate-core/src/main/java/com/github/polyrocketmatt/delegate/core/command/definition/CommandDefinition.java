package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;

/**
 * @author Matthias Kovacic
 *
 * A command definition is a command attribute that is used
 * for command identification and execution. The identifier
 * must be unique.
 *
 * @param <T> The type of the command definition.
 */
public abstract class CommandDefinition<T> extends CommandAttribute {

    private final T value;

    public CommandDefinition(String identifier, T value) {
        super(identifier);

        this.value = value;
    }

    public T getValue() {
        return value;
    }

}
