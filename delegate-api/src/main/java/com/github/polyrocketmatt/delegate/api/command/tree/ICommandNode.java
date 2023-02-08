// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.tree;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;

import java.util.List;

public interface ICommandNode {

    <T extends ICommandNode> List<T> getChildren();

    CommandDefinition<String> getNameDefinition();

    CommandDefinition<String> getDescriptionDefinition();

}
