package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.data.ActionItem;
import com.github.polyrocketmatt.delegate.core.data.ActionResult;
import com.github.polyrocketmatt.delegate.core.data.Argument;
import com.github.polyrocketmatt.delegate.core.data.FailureActionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.lang.Math.min;

/**
 * A {@link Function}-based command action that takes arguments and yields a result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class FunctionAction extends CommandAction {

    private final Function<List<Argument<?>>, ?> action;

    /**
     * Creates a new {@link FunctionAction} with the given identifier, precedence and {@link Function}.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     * @param action The {@link Function} that will be executed.
     */
    public FunctionAction(String identifier, int precedence, Function<List<Argument<?>>, ?> action) {
        super(identifier, precedence);
        this.action = action;
    }

    /**
     * Creates a new {@link FunctionAction} with the given identifier and {@link Function} and
     * a default precedence of 0.
     *
     * @param identifier The identifier of the command action.
     * @param action The {@link Function} that will be executed.
     */
    public FunctionAction(String identifier, Function<List<Argument<?>>, ?> action) {
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
            return new ActionItem<>(ActionResult.Result.SUCCESS, action.apply(inputItems));
        } catch (Exception ex) {
            return new FailureActionResult(ex);
        }
    }

}
