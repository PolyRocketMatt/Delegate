package com.github.polyrocketmatt.delegate.core.command.properties;

import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.utils.Bufferable;

public abstract class CommandProperty extends CommandAttribute implements Bufferable {

    public CommandProperty(String identifier) {
        super(identifier);
    }

}
