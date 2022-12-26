package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.core.command.argument.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;

import java.util.function.Function;

public abstract class ArgumentRule<I, O> {

    private final Function<RuleIO<I>, RuleIO<O>> rule;

    public ArgumentRule(Function<RuleIO<I>, RuleIO<O>> rule) {
        this.rule = rule;
    }

    public Function<RuleIO<I>, RuleIO<O>> getRule() {
        return rule;
    }

    public abstract ArgumentRuleResult interpretResult(CommandArgument<?> argument, RuleIO<I> input, RuleIO<?> output);

}
