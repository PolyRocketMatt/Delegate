// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.properties;

import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;

/**
 * Represents a {@link CommandProperty} that allows the command to ignore null arguments.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class IgnoreNullProperty extends CommandProperty{

    private static final String IGNORE_NULL_IDENTIFIER = "ignoreNull";

    /**
     * Creates a new {@link IgnoreNullProperty}.
     */
    public IgnoreNullProperty() {
        super(IGNORE_NULL_IDENTIFIER);
    }

}
