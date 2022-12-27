package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.data.ActionResult;
import com.github.polyrocketmatt.delegate.core.data.FailureActionResult;
import com.github.polyrocketmatt.delegate.core.data.SuccessActionResult;

import java.util.List;
import java.util.function.Supplier;

public class SupplierAction<T> extends CommandAction {

    private final Supplier<T> action;

    public SupplierAction(String identifier, int precedence, Supplier<T> action) {
        super(identifier, precedence);
        this.action = action;
    }

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
