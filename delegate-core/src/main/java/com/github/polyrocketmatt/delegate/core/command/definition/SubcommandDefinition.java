// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommandBuilder;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;
import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;

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
    public SubcommandDefinition(DelegateCommandBuilder chain) { super(newId(), chain); }

    /**
     * Creates a new subcommand definition with the given name and description.
     *
     * @param name The name of the subcommand.
     * @param description The description of the subcommand.
     */
    public SubcommandDefinition(String name, String description) {
        super(newId(), (DelegateCommandBuilder) getDelegate().getPlatform().createCommand(name, description));
    }

    @Override
    public String toString() {
        return "SubcommandDefinition{" +
                "id='" + getIdentifier() + '\'' +
                '}';
    }
}
