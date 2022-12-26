package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
public interface DelegateCommand {

    NameDefinition getNameDefinition();

    DescriptionDefinition getDescriptionDefinition();

}
