package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.data.ActionItem;
import com.github.polyrocketmatt.delegate.core.exception.ArgumentParseException;

import java.util.function.Consumer;

public interface ArgumentParser<T> {

    default ActionItem<T> parse(String input, Consumer<Exception> onFail) {
        return null;
    }

    default ArgumentParseException onFail(String input, Exception nestedException) {
        return (nestedException == null) ?
                new ArgumentParseException("Failed to parse argument \"%s\"".formatted(input))
            :
                new ArgumentParseException("Failed to parse argument \"%s\"".formatted(input), nestedException);
    }

}
