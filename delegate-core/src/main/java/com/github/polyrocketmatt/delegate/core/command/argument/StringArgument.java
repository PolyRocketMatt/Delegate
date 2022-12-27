package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.DefaultRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.github.polyrocketmatt.delegate.core.data.Argument;
import com.github.polyrocketmatt.delegate.core.utils.ArrayUtils;

import java.util.List;
import java.util.function.Consumer;

public class StringArgument extends CommandArgument<String> {

    public StringArgument(String identifier, String description) {
        super(identifier, description, new NonNullRule());
    }

    public StringArgument(String identifier, String argumentDescription, String defaultValue) {
        super(identifier, argumentDescription, new DefaultRule<>(defaultValue));
    }

    public StringArgument(String identifier, String argumentDescription, ArgumentRule<String, ?> rules) {
        super(identifier, argumentDescription, rules);
    }

    public StringArgument(String identifier, String argumentDescription, String defaultValue, ArgumentRule<String, ?> rules) {
        super(identifier, argumentDescription, ArrayUtils.combine(List.of(new DefaultRule<>(defaultValue)), List.of(rules)));
    }

    @Override
    public Argument<String> parse(String input, Consumer<Exception> onFail) {
        return new Argument<>(input);
    }

    public static StringArgument of(String identifier, String description) {
        return new StringArgument(identifier, description);
    }

}
