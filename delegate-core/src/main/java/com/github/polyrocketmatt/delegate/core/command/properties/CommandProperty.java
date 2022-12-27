package com.github.polyrocketmatt.delegate.core.command.properties;

import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.utils.Bufferable;

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

}
