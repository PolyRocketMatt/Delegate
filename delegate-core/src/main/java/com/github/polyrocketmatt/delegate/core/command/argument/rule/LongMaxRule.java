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

public class LongMaxRule extends ArgumentRule<Boolean> {

    private final long max;
    private final boolean inclusive;

    /**
     * Creates a new rule that checks if an input is a number and is smaller than the specified value.
     *
     * @param max The maximum value.
     * @param inclusive Whether the minimum value is inclusive.
     */
    public LongMaxRule(long max, boolean inclusive) {
        super(input -> {
            try {
                long value = Long.parseLong(input);
                return new RuleData<>(inclusive ? max >= value : max > value);
            } catch (NumberFormatException ex) { return new RuleData<>(false); }
        });

        this.max = max;
        this.inclusive = inclusive;
    }

    /**
     * Creates a new rule that checks if an input is a number and is smaller than the specified value.
     *
     * @param max The maximum value.
     */
    public LongMaxRule(long max) {
        this(max, true);
    }

    public long getMax() {
        return max;
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
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Value was null or not a number less than the maximum: %s (max %s)".formatted(input, max));
        return new ArgumentRuleResult(ArgumentRuleResult.Result.SUCCESS, "Successfully passed %s".formatted(getClass().getSimpleName()));
    }
}
