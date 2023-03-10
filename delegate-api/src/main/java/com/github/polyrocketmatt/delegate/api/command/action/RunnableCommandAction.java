// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.action;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Defines a function that runs some block of code given the expected
 * {@link CommandArgument}s and the actual inputs provided by the user
 * and returns an {@link ActionItem} that can be used to determine
 * if the command was successful or not.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public interface RunnableCommandAction {

    /**
     * Runs the command action with the given arguments, inputs and
     * returns an {@link ActionItem} that can be used to determine
     * if the command was successful or not.
     *
     * @param commander The {@link CommanderEntity} that dispatched the command.
     * @param arguments The expected arguments for the command.
     * @param inputs The actual inputs provided by the user.
     * @return The result of the command.
     */
    @NotNull ActionItem<?> run(@NotNull CommanderEntity commander, @NotNull List<Argument<?>> arguments);

}
