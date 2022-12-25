package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import java.util.Objects;

public class NonNullRule<T> extends ArgumentRule<T, Boolean> {

    public NonNullRule() {
        super(Objects::nonNull);
    }

}
