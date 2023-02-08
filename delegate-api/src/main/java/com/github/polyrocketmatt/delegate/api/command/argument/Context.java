// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.argument;

import java.util.List;
import java.util.Optional;

public class Context {

    private final List<Argument<?>> arguments;

    public Context(List<Argument<?>> arguments) {
        this.arguments = arguments;
    }

    @SuppressWarnings("unchecked")
    public <T> T find(String identifier) {
        Optional<Argument<?>> element = arguments.stream()
                .filter(argument -> argument.identifier().equals(identifier))
                .findFirst();
        return element.map(argument -> (T) argument.output()).orElse(null);
    }

    public Argument<?> get(int index) {
        return arguments.get(index);
    }

    public int size() {
        return arguments.size();
    }

}
