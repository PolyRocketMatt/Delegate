package com.github.polyrocketmatt.delegate.api.command.tree;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;

public interface ICommandNode {

    CommandDefinition<String> getNameDefinition();

    CommandDefinition<String> getDescriptionDefinition();

}
