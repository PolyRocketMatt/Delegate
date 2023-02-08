// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommandBuilder;

/**
 * Represents a {@link CommandDefinition} that describes a subcommand
 * for the command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class SubcommandDefinition extends CommandDefinition<DelegateCommandBuilder> {

    /**
     * Creates a new subcommand definition with a {@link DelegateCommandBuilder}
     * that describes the subcommand.
     *
     * @param chain The chain of attributes that describe the subcommand.
     */
    public SubcommandDefinition(DelegateCommandBuilder chain) { super("subCommand", chain); }

}
