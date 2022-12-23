package com.github.polyrocketmatt.delegate.core.command.argument;

import java.util.function.Consumer;

public class StringArgument extends CommandArgument<String> {

    public StringArgument(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public String parse(String input, Consumer<Exception> onFail) {
        return input;
    }

}
