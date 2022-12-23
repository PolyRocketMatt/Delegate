package com.github.polyrocketmatt.delegate.core.command.argument;

import java.util.function.Consumer;

public class FloatArgument extends CommandArgument<Float> {

    public FloatArgument(String identifier, String argumentDescription) {
        super(identifier, argumentDescription);
    }

    @Override
    public Float parse(String input, Consumer<Exception> onFail) {
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException ex) {
            onFail.accept(ex);
        }

        return null;
    }

}
