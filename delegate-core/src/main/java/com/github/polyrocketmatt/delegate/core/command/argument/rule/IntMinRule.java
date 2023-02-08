// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;

public class IntMinRule extends ArgumentRule<Boolean> {

    /**
     * Creates a new rule that checks if an input is a number and is greater than the specified value.
     *
     * @param min The minimum value.
     * @param inclusive Whether the minimum value is inclusive.
     */
    public IntMinRule(int min, boolean inclusive) {
        super(input -> {
            try {
                int value = Integer.parseInt(input);
                return new RuleData<>(inclusive ? min <= value : min < value);
            } catch (NumberFormatException ex) { return new RuleData<>(false); }
        });
    }

    /**
     * Creates a new rule that checks if an input is a number and is greater than the specified value.
     *
     * @param min The minimum value.
     */
    public IntMinRule(int min) {
        this(min, true);
    }

    @Override
    public ArgumentRuleResult interpretResult(CommandArgument<?> argument, String input, RuleData<?> output) {
        if (!(output.value() instanceof Boolean result))
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Expected result of rule did not match");
        if (!result)
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Value was null or not a number greater than the minimum");
        return new ArgumentRuleResult(ArgumentRuleResult.Result.SUCCESS, "Successfully passed %s".formatted(getClass().getSimpleName()));
    }
}
