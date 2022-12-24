package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.utils.Bufferable;

import java.util.function.Consumer;

public abstract class CommandArgument<T> extends CommandAttribute implements Bufferable {

    private final String argumentDescription;
    private final boolean isNullable;

    public CommandArgument(String identifier, String argumentDescription, boolean isNullable) {
        super(identifier);
        this.argumentDescription = argumentDescription;
        this.isNullable = isNullable;
    }

    public String getArgumentDescription() {
        return argumentDescription;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public T parse(String input) {
        return parse(input, ex -> {
            throw new ArgumentParseException("Failed to parse argument \"%s\"".formatted(input), ex);
        });
    }

    public abstract T parse(String input, Consumer<Exception> onFail);

}
