// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.properties;

import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;

/**
 * Represents a {@link CommandProperty} that allows the command to be executed asynchronously.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class AsyncProperty extends CommandProperty {

    private static final String ASYNC_IDENTIFIER = "executeAsync";

    /**
     * Creates a new {@link AsyncProperty}.
     */
    public AsyncProperty() {
        super(ASYNC_IDENTIFIER);
    }

}
