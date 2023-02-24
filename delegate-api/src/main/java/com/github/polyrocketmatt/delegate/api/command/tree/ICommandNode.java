// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.tree;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ICommandNode {

    <T extends ICommandNode> @NotNull List<T> getChildren();

    @NotNull CommandDefinition<String> getNameDefinition();

    @NotNull CommandDefinition<String> getDescriptionDefinition();

}
