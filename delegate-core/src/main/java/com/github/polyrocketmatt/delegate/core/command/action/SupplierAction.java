package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.ActionResult;
import com.github.polyrocketmatt.delegate.api.command.data.FailureActionResult;
import com.github.polyrocketmatt.delegate.api.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

/**
 * A {@link Supplier}-based command action that does not take any arguments and only yields a result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class SupplierAction<T> extends CommandAction {

    private final Supplier<T> action;

    /**
     * Creates a new {@link SupplierAction} with an identifier and {@link Consumer} and
     * a default precedence of 0.
     *
     * @param identifier The identifier of the command action.
     * @param action The {@link Consumer} that will be executed.
     */
    public SupplierAction(Supplier<T> action) {
        super(newId(), 0);
        this.action = action;
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param precedence The precedence of the command action.
     * @param action The {@link Consumer} that will be executed.
     */
    public SupplierAction(int precedence, Supplier<T> action) {
        super(newId(), precedence);
        this.action = action;
    }

    /**
     * Creates a new {@link SupplierAction} with an identifier, precedence and {@link Supplier}.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     * @param action The {@link Consumer} that will be executed.
     */
    public SupplierAction(String identifier, int precedence, Supplier<T> action) {
        super(identifier, precedence);
        this.action = action;
    }

    @Override
    public ActionItem<?> run(CommanderEntity commander, CommandBuffer<CommandArgument<?>> arguments, List<String> inputs) {
        try {
            return new ActionItem<>(ActionItem.Result.SUCCESS, action.get());
        } catch (Exception ex) {
            return new FailureActionResult(ex);
        }
    }

}
