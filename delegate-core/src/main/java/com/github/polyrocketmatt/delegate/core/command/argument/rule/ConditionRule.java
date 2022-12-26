package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.core.command.argument.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;

import java.util.function.Function;

public class ConditionRule extends ArgumentRule<String, Boolean> {

    public ConditionRule(Function<String, Boolean> condition) {
        super(input -> new RuleOutput<>(condition.apply(input.input())));
    }

    @Override
    public ArgumentRuleResult interpretResult(CommandArgument<?> argument, RuleInput<String> input, RuleOutput<?> output) {
        if (!(output.result() instanceof Boolean result))
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Expected result of rule did not match");
        if (!result)
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Condition was not met");
        return new ArgumentRuleResult(ArgumentRuleResult.Result.SUCCESS, "Successfully passed %s".formatted(getClass().getSimpleName()));
    }

}
