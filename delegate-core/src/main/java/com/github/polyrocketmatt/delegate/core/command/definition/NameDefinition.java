// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

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
        super("commandName_" + newId(), name);
    }

    @Override
    public String toString() {
        return "NameDefinition{" +
                "name='" + getValue() + '\'' +
                ", id='" + getIdentifier() + '\'' +
                '}';
    }
}
