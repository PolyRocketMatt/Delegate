package com.github.polyrocketmatt.delegate.core.command.argument;

import java.util.function.Consumer;

public class StringArgument extends CommandArgument<String> {

    public StringArgument(String identifier, String description, boolean isNullable) {
        super(identifier, description, isNullable);
    }

    public StringArgument(String identifier, String description) {
        super(identifier, description, false);
    }

    @Override
    public String parse(String input, Consumer<Exception> onFail) {
        return input;
    }

}
