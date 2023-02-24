// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.argument.rule;

import org.jetbrains.annotations.NotNull;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Represents data that can be passed from and to an {@link ArgumentRule}.
 *
 * @param value The value of the data.
 * @param <T> The type of the data.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public record RuleData<T>(@NotNull T value) {

    public RuleData {
        validate("value", Object.class, value);
    }

}
