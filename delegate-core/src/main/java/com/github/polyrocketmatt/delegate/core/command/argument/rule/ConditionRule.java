package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;

import java.util.function.Function;

/**
 * A rule that checks if a condition on an input string is met.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class ConditionRule extends ArgumentRule<Boolean> {

    /**
     * Creates a new rule that checks if a condition on an input string is met.
     *
     * @param condition The condition to check.
     */
    public ConditionRule(Function<String, Boolean> condition) {
        super(input -> new RuleData<>(condition.apply(input)));
    }

    @Override
    public ArgumentRuleResult interpretResult(CommandArgument<?> argument, String input, RuleData<?> output) {
        if (!(output.value() instanceof Boolean result))
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Expected result of rule did not match");
        if (!result)
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Condition was not met");
        return new ArgumentRuleResult(ArgumentRuleResult.Result.SUCCESS, "Successfully passed %s".formatted(getClass().getSimpleName()));
    }

}
