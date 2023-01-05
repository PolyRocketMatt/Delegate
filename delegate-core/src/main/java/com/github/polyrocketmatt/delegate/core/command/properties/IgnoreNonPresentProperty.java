package com.github.polyrocketmatt.delegate.core.command.properties;

import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;

/**
 * Represents a {@link CommandProperty} that allows the command to ignore non-present arguments.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class IgnoreNonPresentProperty extends CommandProperty {

    private static final String IGNORE_NON_PRESENT_IDENTIFIER = "ignoreNonPresent";

    /**
     * Creates a new {@link IgnoreNullProperty}.
     */
    public IgnoreNonPresentProperty() {
        super(IGNORE_NON_PRESENT_IDENTIFIER);
    }

}
