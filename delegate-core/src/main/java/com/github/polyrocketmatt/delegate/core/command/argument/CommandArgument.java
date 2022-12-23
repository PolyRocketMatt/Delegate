package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.exception.ArgumentParseException;

import java.util.function.Consumer;

public abstract class CommandArgument<T> extends CommandAttribute {

    private final String argumentDescription;

    public CommandArgument(String identifier, String argumentDescription) {
        super(identifier);
        this.argumentDescription = argumentDescription;
    }

    public String getArgumentDescription() {
        return argumentDescription;
    }

    public T parse(String input) {
        return parse(input, ex -> {
            throw new ArgumentParseException("Failed to parse argument \"%s\"".formatted(input), ex);
        });
    }

    public abstract T parse(String input, Consumer<Exception> onFail);

}
