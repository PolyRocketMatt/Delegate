package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.core.command.argument.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;

import java.util.function.Function;

public abstract class ArgumentRule<I, O> {

    private final Function<RuleData<I>, RuleData<O>> rule;

    public ArgumentRule(Function<RuleData<I>, RuleData<O>> rule) {
        this.rule = rule;
    }

    public Function<RuleData<I>, RuleData<O>> getRule() {
        return rule;
    }

    public abstract ArgumentRuleResult interpretResult(CommandArgument<?> argument, RuleData<I> input, RuleData<?> output);

}
