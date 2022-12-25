package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.data.ActionItem;

import java.util.function.Consumer;

public class FloatArgument extends CommandArgument<Float> {

    public FloatArgument(String identifier, String argumentDescription, boolean isNullable) {
        super(identifier, argumentDescription, isNullable);
    }

    public FloatArgument(String identifier, String argumentDescription) {
        super(identifier, argumentDescription, false);
    }

    @Override
    public ActionItem<Float> parse(String input, Consumer<Exception> onFail) {
        try {
            return new ActionItem<>(Float.parseFloat(input));
        } catch (NumberFormatException ex) {
            onFail.accept(ex);
        }

        return null;
    }

}
