// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

public class AliasDefinition extends CommandDefinition<String> {

    /**
     * Creates a new {@link AliasDefinition} with an alias for a command.
     *
     * @param alias The alias of the command.
     */
    public AliasDefinition(String alias) {
        super(newId(), alias);
    }
}
