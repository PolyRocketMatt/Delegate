package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.github.polyrocketmatt.delegate.core.data.ActionItem;

import java.util.function.Consumer;

public class StringArgument extends CommandArgument<String> {

    public StringArgument(String identifier, String description) {
        super(identifier, description, new NonNullRule());
    }

    @Override
    public ActionItem<String> parse(String input, Consumer<Exception> onFail) {
        return new ActionItem<>(input);
    }

    public static StringArgument of(String identifier, String description) {
        return new StringArgument(identifier, description);
    }

}
