// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.impl.command;

import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommandBuilder;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;

public class BukkitCommandFactory implements ICommandFactory {

    /**
     * Creates a new {@link DelegateCommandBuilder} with the given name and description.
     *
     * @param name The name of the command.
     * @param description The description of the command.
     * @return The new {@link DelegateCommandBuilder}.
     */
    @Override
    public BukkitCommandBuilder create(String name, String description) {
        BukkitCommandBuilder builder = new BukkitCommandBuilder();

        builder.withDefinition(new NameDefinition(name));
        builder.withDefinition(new DescriptionDefinition(description));

        return builder;
    }
}
