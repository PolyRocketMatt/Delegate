package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.github.polyrocketmatt.delegate.core.data.ActionItem;

import java.util.List;
import java.util.function.Consumer;

public class StringArgument extends CommandArgument<String> {

    public StringArgument(String identifier, String description) {
        super(identifier, description, List.of(new NonNullRule<String>()));
    }

    @Override
    public ActionItem<String> parse(String input, Consumer<Exception> onFail) {
        return new ActionItem<>(input);
    }

}
