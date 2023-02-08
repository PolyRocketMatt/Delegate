// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.AttributeType;

/**
 * Represents a command attribute with a certain type.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public interface ICommandAttribute {

    /**
     * Gets the type of the attribute.
     *
     * @return The type of the attribute.
     */
    default AttributeType getType() {
        return AttributeType.UNKNOWN;
    }

}
