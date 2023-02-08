// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;

public interface IDelegateCommand {

    CommandDefinition<String> getNameDefinition();

    CommandDefinition<String> getDescriptionDefinition();

}
