package com.github.polyrocketmatt.delegate.core.command;

public abstract class CommandAttribute {

    private String identifier;

    public CommandAttribute(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
