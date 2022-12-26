package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.DefaultRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.github.polyrocketmatt.delegate.core.data.ActionItem;
import com.github.polyrocketmatt.delegate.core.utils.ArrayUtils;

import java.util.List;
import java.util.function.Consumer;

public class IntArgument extends CommandArgument<Integer> {

    public IntArgument(String identifier, String argumentDescription) {
        super(identifier, argumentDescription, new NonNullRule());
    }

    public IntArgument(String identifier, String argumentDescription, Integer defaultValue) {
        super(identifier, argumentDescription, new DefaultRule<>(defaultValue));
    }

    public IntArgument(String identifier, String argumentDescription, ArgumentRule<String, ?> rules) {
        super(identifier, argumentDescription, rules);
    }

    public IntArgument(String identifier, String argumentDescription, Integer defaultValue, ArgumentRule<String, ?> rules) {
        super(identifier, argumentDescription, ArrayUtils.combine(List.of(new DefaultRule<>(defaultValue)), List.of(rules)));
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
