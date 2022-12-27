package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.data.ActionResult;
import com.github.polyrocketmatt.delegate.core.command.data.FailureActionResult;
import com.github.polyrocketmatt.delegate.core.command.data.SuccessActionResult;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A {@link Supplier}-based command action that does not take any arguments and only yields a result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class SupplierAction<T> extends CommandAction {

    private final Supplier<T> action;

    /**
     * Creates a new {@link SupplierAction} with the given identifier, precedence and {@link Supplier}.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     * @param action The {@link Consumer} that will be executed.
     */
    public SupplierAction(String identifier, int precedence, Supplier<T> action) {
        super(identifier, precedence);
        this.action = action;
    }

    /**
     * Creates a new {@link SupplierAction} with the given identifier and {@link Consumer} and
     * a default precedence of 0.
     *
     * @param identifier The identifier of the command action.
     * @param action The {@link Consumer} that will be executed.
     */
    public SupplierAction(String identifier, Supplier<T> action) {
        super(identifier, 0);
        this.action = action;
    }

    @Override
    public ActionResult run(CommandBuffer<CommandArgument<?>> arguments, List<String> inputs) {
        try {
            action.get();
        } catch (Exception ex) {
            return new FailureActionResult(ex);
        }

        return new SuccessActionResult();
    }

}
