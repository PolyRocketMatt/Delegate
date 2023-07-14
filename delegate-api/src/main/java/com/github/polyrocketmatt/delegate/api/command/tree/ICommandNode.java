// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.tree;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@API(status = API.Status.STABLE, since = "0.0.1")
public interface ICommandNode {

    <T extends ICommandNode> @NotNull List<T> getChildren();

    int size();

    @NotNull CommandDefinition<String> getNameDefinition();

    @NotNull CommandDefinition<String> getDescriptionDefinition();

    @NotNull CommandDefinition<String>[] getAliasDefinitions();

}
