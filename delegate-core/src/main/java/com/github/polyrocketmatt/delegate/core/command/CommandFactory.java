package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;

public class CommandFactory {

    public static CommandAttributeChain create() {
        return new CommandAttributeChain();
    }

    public static CommandAttributeChain create(String name) {
        return new CommandAttributeChain().append(new NameDefinition(name));
    }

    public static CommandAttributeChain create(String name, String description) {
        return new CommandAttributeChain().append(new NameDefinition(name)).append(new DescriptionDefinition(description));
    }

}
