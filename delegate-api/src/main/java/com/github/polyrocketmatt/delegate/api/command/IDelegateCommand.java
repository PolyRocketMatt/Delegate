// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import org.jetbrains.annotations.NotNull;

public interface IDelegateCommand {

    @NotNull CommandDefinition<String> getNameDefinition();

    @NotNull CommandDefinition<String> getDescriptionDefinition();

}
