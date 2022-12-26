package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.core.command.argument.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;

import java.util.Objects;

public class NonNullRule extends ArgumentRule<String, Boolean> {

    public NonNullRule() {
        super(input -> new RuleOutput<>(Objects.nonNull(input)));
    }

    @Override
    public ArgumentRuleResult interpretResult(CommandArgument<?> argument, RuleInput<String> input, RuleOutput<?> output) {
        if (!(output.result() instanceof Boolean result))
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Expected result of rule did not match");
        if (!result)
            return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Non-nullity of input was not met");
        return new ArgumentRuleResult(ArgumentRuleResult.Result.SUCCESS, "Successfully passed %s".formatted(getClass().getSimpleName()));
    }

}
