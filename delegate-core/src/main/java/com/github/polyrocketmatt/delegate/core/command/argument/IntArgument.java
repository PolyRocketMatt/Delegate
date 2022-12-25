package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.data.ActionItem;

import java.util.function.Consumer;

public class IntArgument extends CommandArgument<Integer> {

    public IntArgument(String identifier, String argumentDescription, boolean isNullable) {
        super(identifier, argumentDescription, isNullable);
    }

    public IntArgument(String identifier, String argumentDescription) {
        this(identifier, argumentDescription, false);
    }

    @Override
    public ActionItem<Integer> parse(String input, Consumer<Exception> onFail) {
        try {
            return new ActionItem<>(Integer.parseInt(input));
        } catch (NumberFormatException ex) {
            onFail.accept(ex);
        }

        return null;
    }
}
