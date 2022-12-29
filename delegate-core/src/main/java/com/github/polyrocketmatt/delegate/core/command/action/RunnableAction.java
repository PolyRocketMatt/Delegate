package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.api.command.data.ActionResult;
import com.github.polyrocketmatt.delegate.api.command.data.FailureActionResult;
import com.github.polyrocketmatt.delegate.api.command.data.SuccessActionResult;

import java.util.List;
import java.util.function.Consumer;

/**
 * A {@link Consumer}-based command action that does not yield a result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class RunnableAction extends CommandAction {

    private final Runnable action;

    /**
     * Creates a new {@link RunnableAction} with an identifier, precedence and {@link Runnable}.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     * @param action The {@link Runnable} that will be executed.
     */
    public RunnableAction(String identifier, int precedence, Runnable action) {
        super(identifier, precedence);
        this.action = action;
    }

    /**
     * Creates a new {@link RunnableAction} with an identifier and {@link Runnable} and
     * a default precedence of 0.
     *
     * @param identifier The identifier of the command action.
     * @param action The {@link Runnable} that will be executed.
     */
    public RunnableAction(String identifier, Runnable action) {
        super(identifier, 0);
        this.action = action;
    }

    @Override
    public ActionResult run(CommandBuffer<CommandArgument<?>> arguments, List<String> inputs) {
        try {
            action.run();
        } catch (Exception ex) {
            return new FailureActionResult(ex);
        }

        return new SuccessActionResult();
    }

}
