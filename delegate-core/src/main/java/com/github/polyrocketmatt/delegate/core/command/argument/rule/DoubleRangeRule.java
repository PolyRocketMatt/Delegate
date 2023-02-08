// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;

public class DoubleRangeRule extends ArgumentRule<Boolean> {

    /**
     * Creates a new rule that checks if an input is a number and is within the specified range.
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @param leftInclusive Whether the minimum value is inclusive.
     * @param rightInclusive Whether the maximum value is inclusive.
     */
    public DoubleRangeRule(double min, double max, boolean leftInclusive, boolean rightInclusive) {
        super(input -> {
            try {
                double value = Double.parseDouble(input);
                return new RuleData<>((leftInclusive ? min <= value : min < value)
                        && (rightInclusive ? value <= max : value < max));
            } catch (NumberFormatException ex) { return new RuleData<>(false); }
        });
    }

    /**
     * Creates a new rule that checks if an input is a number and is within the specified range.
     *
     * @param min The minimum value.
     * @param max The maximum value.
     */
    public DoubleRangeRule(double min, double max) {
        this(min, max, true, true);
    }

    @Override
    public ArgumentRuleResult interpretResult(CommandArgument<?> argument, String input, RuleData<?> output) {
        if (!(output.value() instanceof Boolean result))
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Expected result of rule did not match");
        if (!result)
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Value was null or not a number inside the valid range");
        return new ArgumentRuleResult(ArgumentRuleResult.Result.SUCCESS, "Successfully passed %s".formatted(getClass().getSimpleName()));
    }
}
