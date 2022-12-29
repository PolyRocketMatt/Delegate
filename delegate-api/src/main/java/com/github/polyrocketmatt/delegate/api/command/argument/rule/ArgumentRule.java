package com.github.polyrocketmatt.delegate.api.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;

import java.util.function.Function;

/**
 * Represents a rule that must apply to a command argument.
 * The rule takes a {@link Function} with the input object of
 * type {@link I} and return object of type {@link O}.
 *
 * @param <I> The input type of the rule.
 * @param <O> The output type of the rule.
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public abstract class ArgumentRule<I, O> {

    private final Function<RuleData<I>, RuleData<O>> rule;

    /**
     * Creates a new argument rule with the given {@link Function} which
     * represents the rule to apply.
     *
     * @param rule The {@link Function} which represents the rule to apply.
     */
    public ArgumentRule(Function<RuleData<I>, RuleData<O>> rule) {
        this.rule = rule;
    }

    /**
     * Gets the {@link Function} which represents the rule to apply.
     *
     * @return The {@link Function} which represents the rule to apply.
     */
    public Function<RuleData<I>, RuleData<O>> getRule() {
        return rule;
    }

    /**
     * Interprets the result of the application of the rule and encodes the
     * interpretation in an {@link ArgumentRuleResult}.
     *
     * @param argument The {@link CommandArgument} the rule was assigned to.
     * @param input The input object of the rule.
     * @param output The output object of the rule.
     * @return The interpretation of the application of the rule in an {@link ArgumentRuleResult}.
     */
    public abstract ArgumentRuleResult interpretResult(CommandArgument<?> argument, RuleData<I> input, RuleData<?> output);

}
