// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.argument;

import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

@API(status = API.Status.STABLE, since = "0.0.1")
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
        if (element.isEmpty())
            throw new IllegalArgumentException("No argument with identifier " + identifier + " found");
        return element.map(value -> (T) value.output()).orElse(null);
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
        if (element.isEmpty())
            throw new IllegalArgumentException("No argument with identifier " + identifier + " found with type " + type.getSimpleName());
        return element.map(value -> (T) value.output()).orElse(null);
    }

    public @NotNull Argument<?> get(int index) throws IndexOutOfBoundsException {
        return arguments.get(index);
    }

    public int size() {
        return arguments.size();
    }

}
