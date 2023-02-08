// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.definition;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;

/**
 * Represents a command definition that describes the command
 * in some way.
 *
 * @param <T> The type of the definition.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public abstract class CommandDefinition<T> extends CommandAttribute {

    private final T value;

    /**
     * Creates a new command definition with an identifier and value.
     *
     * @param identifier The identifier of the definition.
     * @param value The value of the definition.
     */
    public CommandDefinition(String identifier, T value) {
        super(identifier);

        this.value = value;
    }

    /**
     * Gets the value of the definition.
     *
     * @return The value of the definition.
     */
    public T getValue() {
        return value;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.DEFINITION;
    }
}
