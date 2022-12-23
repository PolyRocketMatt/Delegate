package com.github.polyrocketmatt.delegate.core.command.definition;

public class NameDefinition extends CommandDefinition<String> {

    public NameDefinition(String name) {
        super("commandName", name);
    }

}
