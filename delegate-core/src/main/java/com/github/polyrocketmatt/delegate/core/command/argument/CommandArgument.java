package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.RuleInput;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.RuleOutput;
import com.github.polyrocketmatt.delegate.core.data.ActionItem;
import com.github.polyrocketmatt.delegate.core.utils.Bufferable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class CommandArgument<T> extends CommandAttribute implements Bufferable, ArgumentParser<T> {

    private final String argumentDescription;
    private final List<ArgumentRule<String, ?>> argumentRules;

    private ActionItem<T> defaultValue;

    public CommandArgument(String identifier, String argumentDescription) {
        super(identifier);
        this.argumentDescription = argumentDescription;
        this.argumentRules = List.of();
    }

    @SafeVarargs
    public CommandArgument(String identifier, String argumentDescription, ArgumentRule<String, ?>... argumentRules) {
        super(identifier);
        this.argumentDescription = argumentDescription;
        this.defaultValue = null;
        this.argumentRules = Arrays.asList(argumentRules);
    }

    public String getArgumentDescription() {
        return argumentDescription;
    }

    public List<ArgumentRule<String, ?>> getArgumentRules() {
        return argumentRules;
    }

    private void addRule(ArgumentRule<String, ?> rule) {
        this.argumentRules.add(rule);
    }

    public ActionItem<T> getDefault() {
        return defaultValue;
    }

    private void setDefaultValue(ActionItem<T> defaultValue) {
        this.defaultValue = defaultValue;
    }

    public ActionItem<T> parse(String input) {
        return this.parse(input, ex -> { throw onFail(input, ex); });
    }

    @Override
    public abstract ActionItem<T> parse(String input, Consumer<Exception> consumer);

    public boolean parseRules(String input) {
        for (ArgumentRule<String, ?> rule : getArgumentRules()) {
            RuleOutput<?> result = rule.getRule().apply(new RuleInput<>(input));
            ArgumentRuleResult ruleResult = rule.interpretResult(this, new RuleInput<>(input), result);

            if (ruleResult.result() == ArgumentRuleResult.Result.FAILURE)
                throw onFail(ruleResult.info(), null);
        }

        return true;
    }

    public CommandArgument<T> withRule(ArgumentRule<String, ?> rule) {
        this.addRule(rule);
        return this;
    }

    @SafeVarargs
    public final CommandArgument<?> withRules(ArgumentRule<String, ?>... rules) {
        for (ArgumentRule<String, ?> rule : rules)
            this.addRule(rule);
        return this;
    }

    public CommandArgument<T> withDefault(ActionItem<T> defaultValue) {
        this.setDefaultValue(defaultValue);
        return this;
    }

}
