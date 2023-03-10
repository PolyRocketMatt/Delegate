// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Represents a rule that must apply to a command argument.
 * The rule takes a {@link Function} with the input string
 * and return object of type {@link O}.
 *
 * @param <O> The output type of the rule.
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public abstract class ArgumentRule<O> {

    private final Function<String, RuleData<O>> rule;

    /**
     * Creates a new argument rule with the given {@link Function} which
     * represents the rule to apply.
     *
     * @param rule The {@link Function} which represents the rule to apply.
     */
    public ArgumentRule(@NotNull Function<String, RuleData<O>> rule) {
        validate("rule", Function.class, rule);

        this.rule = rule;
    }

    /**
     * Gets the {@link Function} which represents the rule to apply.
     *
     * @return The {@link Function} which represents the rule to apply.
     */
    public @NotNull Function<String, RuleData<O>> getRule() {
        return rule;
    }

    /**
     * Interprets the result of the application of the rule and encodes the
     * interpretation in an {@link ArgumentRuleResult}.
     *
     * @param argument The {@link CommandArgument} the rule was assigned to.
     * @param input The input object of the rule.
     * @param output The output object of the rule.
     * @return The interpretation of the application of the rule in an {@link ArgumentRuleResult}.
     */
    public abstract @NotNull ArgumentRuleResult interpretResult(
            @Nullable CommandArgument<?> argument, @Nullable String input, @NotNull RuleData<?> output);

}
