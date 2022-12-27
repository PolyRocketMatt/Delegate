package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.data.ActionResult;
import com.github.polyrocketmatt.delegate.core.command.data.Argument;
import com.github.polyrocketmatt.delegate.core.command.data.FailureActionResult;
import com.github.polyrocketmatt.delegate.core.command.data.SuccessActionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.Math.min;

/**
 * A {@link Consumer}-based command action that does not yield a result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class ConsumerAction extends CommandAction {

    private final Consumer<List<Argument<?>>> action;

    /**
     * Creates a new {@link ConsumerAction} with the given identifier, precedence and {@link Consumer}.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     * @param action The {@link Consumer} that will be executed.
     */
    public ConsumerAction(String identifier, int precedence, Consumer<List<Argument<?>>> action) {
        super(identifier, precedence);
        this.action = action;
    }

    /**
     * Creates a new {@link ConsumerAction} with the given identifier and {@link Consumer} and
     * a default precedence of 0.
     *
     * @param identifier The identifier of the command action.
     * @param action The {@link Consumer} that will be executed.
     */
    public ConsumerAction(String identifier, Consumer<List<Argument<?>>> action) {
        super(identifier, 0);
        this.action = action;
    }

    @Override
    public ActionResult run(CommandBuffer<CommandArgument<?>> arguments, List<String> inputs) {
        int maxIndex = min(inputs.size(), arguments.size());

        List<Argument<?>> inputItems = new ArrayList<>();
        for (int i = 0; i < maxIndex; i++)
            inputItems.add(arguments.get(i).parse(inputs.get(i)));
        try {
            action.accept(inputItems);
        } catch (Exception ex) {
            return new FailureActionResult(ex);
        }

        return new SuccessActionResult();
    }

}
