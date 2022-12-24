package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;

@FunctionalInterface
public interface DelegateCommand {

    NameDefinition getNameDefinition();

}
