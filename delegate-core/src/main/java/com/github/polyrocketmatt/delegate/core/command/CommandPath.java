package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;

public class CommandPath {

    private final NameDefinition parent;
    private final NameDefinition name;

    public CommandPath(NameDefinition parent, NameDefinition name) {
        this.parent = parent;
        this.name = name;
    }

    public NameDefinition getParent() {
        return parent;
    }

    public NameDefinition getName() {
        return name;
    }
}
