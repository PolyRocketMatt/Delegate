// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.property;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.Bufferable;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

/**
 * Represents a command property that defines special
 * behavior for a command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public abstract class CommandProperty extends CommandAttribute implements Bufferable {

    /**
     * Creates a new command property with an identifier.
     *
     * @param identifier The identifier of the property.
     */
    public CommandProperty(@NotNull String identifier) {
        super(identifier);
    }

    public CommandProperty() {
        super(newId());
    }

    @Override
    public @NotNull AttributeType getType() {
        return AttributeType.PROPERTY;
    }
}
