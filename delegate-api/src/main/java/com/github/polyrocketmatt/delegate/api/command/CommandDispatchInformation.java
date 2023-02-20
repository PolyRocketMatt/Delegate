// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import org.jetbrains.annotations.NotNull;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Record that contains information about a command that has been dispatched
 * on any platform.
 *
 * @param commander The {@link CommanderEntity} that dispatched the command.
 * @param command The command that was dispatched.
 * @param arguments The arguments that were passed to the command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic 
 */
public record CommandDispatchInformation(@NotNull CommanderEntity commander, @NotNull String command,
                                         @NotNull String[] arguments) {

    public CommandDispatchInformation {
        validate(commander, command, arguments);
    }

}
