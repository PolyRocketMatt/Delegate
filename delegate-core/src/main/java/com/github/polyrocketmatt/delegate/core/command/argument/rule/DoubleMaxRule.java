package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;

public class DoubleMaxRule extends ArgumentRule<Boolean> {

    /**
     * Creates a new rule that checks if an input is a number and is smaller than the specified value.
     *
     * @param max The maximum value.
     * @param inclusive Whether the minimum value is inclusive.
     */
    public DoubleMaxRule(double max, boolean inclusive) {
        super(input -> {
            try {
                double value = Double.parseDouble(input);
                return new RuleData<>(inclusive ? max >= value : max > value);
            } catch (NumberFormatException ex) { return new RuleData<>(false); }
        });
    }

    /**
     * Creates a new rule that checks if an input is a number and is smaller than the specified value.
     *
     * @param max The maximum value.
     */
    public DoubleMaxRule(double max) {
        this(max, true);
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
