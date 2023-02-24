// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a command attribute with a certain type.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public interface ICommandAttribute {

    /**
     * Gets the type of the attribute.
     *
     * @return The type of the attribute.
     */
    default @NotNull AttributeType getType() {
        return AttributeType.UNKNOWN;
    }

}
