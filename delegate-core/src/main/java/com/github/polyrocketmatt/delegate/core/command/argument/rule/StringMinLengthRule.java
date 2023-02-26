// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

public class StringMinLengthRule extends ArgumentRule<Boolean> {

    private final int minLength;
    private final boolean inclusive;

    /**
     * Creates a new rule that checks if an input is a number and is greater than the specified value.
     *
     * @param minLength The minimum length of the string.
     * @param inclusive Whether the minimum value is inclusive.
     */
    public StringMinLengthRule(int minLength, boolean inclusive) {
        super(input -> {
            int value = input.length();
            return new RuleData<>(inclusive ? minLength <= value : minLength < value);
        });

        this.minLength = minLength;
        this.inclusive = inclusive;
    }

    /**
     * Creates a new rule that checks if an input is a number and is greater than the specified value.
     *
     * @param min The minimum value.
     */
    public StringMinLengthRule(int min) {
        this(min, true);
    }

    public int getMinLength() {
        return minLength;
    }

    public boolean isInclusive() {
        return inclusive;
    }

    @Override
    public @NotNull ArgumentRuleResult interpretResult(@Nullable CommandArgument<?> argument, @Nullable String input, @NotNull RuleData<?> output) {
        validate("output", RuleData.class, output);

        if (!(output.value() instanceof Boolean result))
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Expected result of rule did not match");
        if (!result)
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "String length was null or not a number greater than the minimum: %s (min %s)".formatted(input, minLength));
        return new ArgumentRuleResult(ArgumentRuleResult.Result.SUCCESS, "Successfully passed %s".formatted(getClass().getSimpleName()));
    }
}
