// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command;

import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

@API(status = API.Status.STABLE, since = "0.0.1")
public interface ICommandFactory {

    /**
     * Creates a new {@link ICommandFactory} with the given name and description.
     *
     * @param name The name of the command.
     * @param description The description of the command.
     * @return The new {@link ICommandFactory}.
     */
    @NotNull ICommandBuilder create(@NotNull String name, @NotNull String description);

    /**
     * Creates a new {@link ICommandFactory} from the given class.
     *
     * @param clazz The class that is to be parsed for command information.
     * @return The new {@link ICommandFactory}.
     */
    @NotNull ICommandBuilder from(@NotNull Class<?> clazz);

}
