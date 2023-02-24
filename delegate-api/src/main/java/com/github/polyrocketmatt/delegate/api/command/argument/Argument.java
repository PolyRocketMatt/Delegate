// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.argument;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Represents an argument of type {@link T} that has been processed by an {@link ArgumentParser}
 * and is ready to be used in a {@link CommandAction}.
 *
 * @param identifier The identifier of the argument.
 * @param output The output of the argument.
 * @param <T> The type of the output.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public record Argument<T>(@NotNull String identifier, @Nullable T output) {

    public Argument {
        validate("identifier", String.class, identifier);
    }

}
