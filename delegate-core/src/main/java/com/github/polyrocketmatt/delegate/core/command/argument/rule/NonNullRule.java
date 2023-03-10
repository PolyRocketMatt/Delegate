// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * A rule that enforces an input string to not be null.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class NonNullRule extends ArgumentRule<Boolean> {

    /**
     * Creates a new rule that enforces an input string to not be null.
     */
    public NonNullRule() {
        super(input -> new RuleData<>(Objects.nonNull(input)));
    }

    @Override
    public @NotNull ArgumentRuleResult interpretResult(@Nullable CommandArgument<?> argument, @Nullable String input, @NotNull RuleData<?> output) {
        validate("output", RuleData.class, output);

        if (!(output.value() instanceof Boolean result))
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Expected result of rule did not match");
        if (!result)
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Non-nullity of value was not met");
        return new ArgumentRuleResult(ArgumentRuleResult.Result.SUCCESS, "Successfully passed %s".formatted(getClass().getSimpleName()));
    }

}
