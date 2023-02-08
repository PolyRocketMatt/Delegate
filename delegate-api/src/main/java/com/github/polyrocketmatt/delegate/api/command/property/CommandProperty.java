// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.property;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.Bufferable;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

/**
 * Represents a command property that defines special
 * behavior for a command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public abstract class CommandProperty extends CommandAttribute implements Bufferable {

    /**
     * Creates a new command property with an identifier.
     *
     * @param identifier The identifier of the property.
     */
    public CommandProperty(String identifier) {
        super(identifier);
    }

    public CommandProperty() {
        super(newId());
    }

    @Override
    public AttributeType getType() {
        return AttributeType.PROPERTY;
    }
}
