package com.github.polyrocketmatt.delegate.api.command.action;

import com.github.polyrocketmatt.delegate.api.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.data.ActionResult;

import java.util.List;

/**
 * Defines a function that runs some block of code given the expected
 * {@link CommandArgument}s and the actual inputs provided by the user
 * and returns an {@link ActionResult} that can be used to determine
 * if the command was successful or not.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public interface RunnableCommandAction {

    /**
     * Runs the command action with the given arguments, inputs and
     * returns an {@link ActionResult} that can be used to determine
     * if the command was successful or not.
     *
     * @param arguments The expected arguments for the command.
     * @param inputs The actual inputs provided by the user.
     * @return The result of the command.
     */
    ActionResult run(CommandBuffer<CommandArgument<?>> arguments, List<String> inputs);

}
