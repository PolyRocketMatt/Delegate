// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.argument.rule;

import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Represents the result of an argument rule.
 *
 * @param result The result of the rule.
 * @param info Additional information about the result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public record ArgumentRuleResult(@NotNull Result result, @NotNull String info) {

    public ArgumentRuleResult {
        validate("result", Result.class, result);
        validate("info", String.class, info);
    }

    /**
     * Enum representing the possible result of applying an argument rule.
     */
    public enum Result {
        SUCCESS,
        FAILURE
    }

}
