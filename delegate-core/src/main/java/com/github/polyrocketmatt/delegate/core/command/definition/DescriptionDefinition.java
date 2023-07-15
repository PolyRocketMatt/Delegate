// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

/**
 * Represents a {@link CommandDefinition} that describes the functionality
 * of the command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class DescriptionDefinition extends CommandDefinition<String> {

    /**
     * Creates a new {@link DescriptionDefinition} with a description.
     *
     * @param description The description of the command.
     */
    public DescriptionDefinition(String description) {
        super("commandDescription_" + newId(), description);
    }

    @Override
    public String toString() {
        return "DescriptionDefinition{" +
                "description='" + getValue() + '\'' +
                ", id='" + getIdentifier() + '\'' +
                '}';
    }

}
