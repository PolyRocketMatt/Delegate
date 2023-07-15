// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.impl.command;

import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommandBuilder;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegateAPI;

public class BukkitCommandFactory implements ICommandFactory {

    /**
     * Creates a new {@link DelegateCommandBuilder} with the given name and description.
     *
     * @param name The name of the command.
     * @param description The description of the command.
     * @return The new {@link DelegateCommandBuilder}.
     */
    @Override
    public @NotNull BukkitCommandBuilder create(@NotNull String name, @NotNull String description) {
        BukkitCommandBuilder builder = new BukkitCommandBuilder();

        builder.withDefinition(new NameDefinition(name));
        builder.withDefinition(new DescriptionDefinition(description));

        return builder;
    }

    /**
     * Creates a new {@link BukkitCommandBuilder} from the given class.
     *
     * @param clazz The class that is to be parsed for command information.
     * @return The new {@link BukkitCommandBuilder}.
     */
    @Override
    public @NotNull BukkitCommandBuilder from(@NotNull Class<?> clazz) {
        List<CommandAttribute> attributes = getDelegateAPI().getAnnotationHandler().process(clazz);
        BukkitCommandBuilder builder = new BukkitCommandBuilder();

        //  Add all the attributes to the builder
        attributes.forEach(builder::with);

        return builder;
    }
}
