// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.impl.command;

import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegateAPI;

public class PaperCommandFactory implements ICommandFactory {

    /**
     * Creates a new {@link PaperCommandBuilder} with the given name and description.
     *
     * @param name The name of the command.
     * @param description The description of the command.
     * @return The new {@link PaperCommandBuilder}.
     */
    @Override
    public @NotNull PaperCommandBuilder create(@NotNull String name, @NotNull String description) {
        PaperCommandBuilder builder = new PaperCommandBuilder();

        builder.withDefinition(new NameDefinition(name));
        builder.withDefinition(new DescriptionDefinition(description));

        return builder;
    }

    /**
     * Creates a new {@link PaperCommandBuilder} from the given class.
     *
     * @param clazz The class that is to be parsed for command information.
     * @return The new {@link PaperCommandBuilder}.
     */
    @Override
    public @NotNull PaperCommandBuilder from(@NotNull Class<?> clazz) {
        List<CommandAttribute> attributes = getDelegateAPI().getAnnotationHandler().process(clazz);
        PaperCommandBuilder builder = new PaperCommandBuilder();

        //  Add all the attributes to the builder
        attributes.forEach(builder::with);

        return builder;
    }
}
