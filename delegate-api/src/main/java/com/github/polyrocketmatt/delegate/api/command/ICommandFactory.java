// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command;

import org.jetbrains.annotations.NotNull;

public interface ICommandFactory {

    /**
     * Creates a new {@link ICommandFactory} with the given name and description.
     *
     * @param name The name of the command.
     * @param description The description of the command.
     * @return The new {@link ICommandFactory}.
     */
    @NotNull ICommandBuilder create(@NotNull String name, @NotNull String description);

}
