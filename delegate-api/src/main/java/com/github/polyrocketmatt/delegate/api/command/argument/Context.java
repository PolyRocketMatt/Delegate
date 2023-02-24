// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.argument;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

public class Context {

    private final List<Argument<?>> arguments;

    public Context(@NotNull List<Argument<?>> arguments) {
        validate("arguments", List.class, arguments);
        arguments.forEach(argument -> validate("element", Argument.class, argument));

        this.arguments = arguments;
    }

    @SuppressWarnings("unchecked")
    public <T> @Nullable T find(@NotNull String identifier) {
        validate("identifier", String.class, identifier);

        Optional<Argument<?>> element = arguments.stream()
                .filter(argument -> argument.identifier().equals(identifier))
                .findFirst();
        return element.map(argument -> (T) argument.output()).orElse(null);
    }

    @SuppressWarnings("unchecked")
    public <T> @Nullable T find(@NotNull String identifier, @NotNull Class<T> type) {
        validate("identifier", String.class, identifier);

        Optional<Argument<?>> element = arguments.stream()
                .filter(Objects::nonNull)
                .filter(argument -> argument.identifier().equals(identifier))
                .filter(argument -> argument.output() != null)
                .filter(argument -> argument.output().getClass().equals(type))
                .findFirst();
        return element.map(argument -> (T) argument.output()).orElse(null);
    }

    public @NotNull Argument<?> get(int index) throws IndexOutOfBoundsException {
        return arguments.get(index);
    }

    public int size() {
        return arguments.size();
    }

}
