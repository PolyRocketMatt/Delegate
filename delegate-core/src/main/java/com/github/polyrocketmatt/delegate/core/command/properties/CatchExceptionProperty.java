// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.properties;

import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;

/**
 * Represents a {@link CommandProperty} that catches any exceptions thrown by the command
 * and returns them as a command capture.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class CatchExceptionProperty extends CommandProperty {

    private static final String CATCH_IDENTIFIER = "executeCatch";

    /**
     * Creates a new {@link CatchExceptionProperty}.
     */
    public CatchExceptionProperty() {
        super(CATCH_IDENTIFIER);
    }

}
