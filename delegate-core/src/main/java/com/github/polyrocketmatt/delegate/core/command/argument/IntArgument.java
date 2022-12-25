package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.github.polyrocketmatt.delegate.core.data.ActionItem;

import java.util.List;
import java.util.function.Consumer;

public class IntArgument extends CommandArgument<Integer> {

    public IntArgument(String identifier, String argumentDescription) {
        super(identifier, argumentDescription, List.of(new NonNullRule<Integer>()));
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

    public static IntArgument of(String identifier, String argumentDescription) {
        return new IntArgument(identifier, argumentDescription);
    }

}
