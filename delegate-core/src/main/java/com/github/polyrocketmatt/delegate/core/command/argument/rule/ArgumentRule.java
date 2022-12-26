package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.core.command.argument.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;

import java.util.function.Function;

public abstract class ArgumentRule<I, O> {

    private final Function<RuleInput<I>, RuleOutput<O>> rule;

    public ArgumentRule(Function<RuleInput<I>, RuleOutput<O>> rule) {
        this.rule = rule;
    }

    public Function<RuleInput<I>, RuleOutput<O>> getRule() {
        return rule;
    }

    public abstract ArgumentRuleResult interpretResult(CommandArgument<?> argument, RuleInput<I> input, RuleOutput<?> output);

}
