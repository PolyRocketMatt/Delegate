package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.DefaultRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.github.polyrocketmatt.delegate.core.data.Argument;
import com.github.polyrocketmatt.delegate.core.utils.ArrayUtils;

import java.util.List;
import java.util.function.Consumer;

public class FloatArgument extends CommandArgument<Float> {

    public FloatArgument(String identifier, String argumentDescription) {
        super(identifier, argumentDescription, new NonNullRule());
    }

    public FloatArgument(String identifier, String argumentDescription, Float defaultValue) {
        super(identifier, argumentDescription, new DefaultRule<>(defaultValue));
    }

    public FloatArgument(String identifier, String argumentDescription, ArgumentRule<String, ?> rules) {
        super(identifier, argumentDescription, rules);
    }

    public FloatArgument(String identifier, String argumentDescription, Float defaultValue, ArgumentRule<String, ?> rules) {
        super(identifier, argumentDescription, ArrayUtils.combine(List.of(new DefaultRule<>(defaultValue)), List.of(rules)));
    }

    @Override
    public Argument<Float> parse(String input, Consumer<Exception> consumer) {
        try {
            return new Argument<>(Float.parseFloat(input));
        } catch (NumberFormatException ex) {
            consumer.accept(ex);
        }

        return null;
    }

    public static FloatArgument of(String identifier, String argumentDescription) {
        return new FloatArgument(identifier, argumentDescription);
    }

    public static FloatArgument of(String identifier, String argumentDescription, float defaultValue) {
        return new FloatArgument(identifier, argumentDescription, defaultValue);
    }

}
