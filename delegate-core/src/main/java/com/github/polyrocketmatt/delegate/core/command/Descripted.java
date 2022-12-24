package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;

@FunctionalInterface
public interface Descripted {

    DescriptionDefinition getDescriptionDefinition();

}
