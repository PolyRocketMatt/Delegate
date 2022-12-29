package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleFormat;

/**
 * A rule that checks if an input complies with a {@link RuleFormat}.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class FormatRule extends ArgumentRule<String, Boolean> {

    /**
     * Creates a new rule that checks if a condition on an input string is met.
     *
     * @param format The format to check.
     */
    public FormatRule(RuleFormat format) {
        super(input -> new RuleData<>(format.matches(input.value())));
    }

    @Override
    public ArgumentRuleResult interpretResult(CommandArgument<?> argument, RuleData<String> input, RuleData<?> output) {
        if (!(output.value() instanceof Boolean result))
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Expected result of rule did not match");
        if (!result)
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Condition was not met");
        return new ArgumentRuleResult(ArgumentRuleResult.Result.SUCCESS, "Successfully passed %s".formatted(getClass().getSimpleName()));
    }

}
