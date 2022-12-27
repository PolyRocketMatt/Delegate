package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.RuleData;
import com.github.polyrocketmatt.delegate.core.command.data.Argument;
import com.github.polyrocketmatt.delegate.core.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.utils.Bufferable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a command argument of a specific type.
 *
 * @param <T> The type of the argument.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public abstract class CommandArgument<T> extends CommandAttribute implements Bufferable, ArgumentParser<T> {

    private final String argumentDescription;
    private final List<ArgumentRule<String, ?>> argumentRules;

    private Argument<T> defaultValue;

    /**
     * Creates a new command argument with an identifier and description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     */
    public CommandArgument(String identifier, String argumentDescription) {
        super(identifier);
        this.argumentDescription = argumentDescription;
        this.argumentRules = List.of();
        this.defaultValue = null;
    }

    /**
     * Creates a new command argument with an identifier, description and
     * argument rules that must be met for the argument to be valid.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param argumentRules The argument rules that must be met for the argument to be valid.
     */
    @SafeVarargs
    public CommandArgument(String identifier, String argumentDescription, ArgumentRule<String, ?>... argumentRules) {
        super(identifier);
        this.argumentDescription = argumentDescription;
        this.argumentRules = Arrays.asList(argumentRules);
        this.defaultValue = null;
    }

    /**
     * Gets the description of the argument.
     *
     * @return The description of the argument.
     */
    public String getArgumentDescription() {
        return argumentDescription;
    }

    /**
     * Gets the argument rules that must be met for the argument to be valid.
     *
     * @return The argument rules that must be met for the argument to be valid.
     */
    public List<ArgumentRule<String, ?>> getArgumentRules() {
        return argumentRules;
    }

    /**
     * Adds an argument rule to the argument.
     *
     * @param rule The argument rule to add.
     */
    private void addRule(ArgumentRule<String, ?> rule) {
        this.argumentRules.add(rule);
    }

    /**
     * Gets the default value of the argument.
     *
     * @return The default value of the argument.
     */
    public Argument<T> getDefault() {
        return defaultValue;
    }

    /**
     * Sets the default value of the argument.
     *
     * @param defaultValue The default value of the argument.
     */
    private void setDefaultValue(Argument<T> defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Parses the argument value from the given input string and returns
     * the parsed value as an {@link Argument}.
     *
     * @param input The input to parse.
     * @return The parsed argument as an {@link Argument}.
     */
    public Argument<T> parse(String input) {
        return this.parse(input, ex -> { throw onFail(input, ex); });
    }

    /**
     * Parses the argument value from the given input and calls the given {@link Consumer}
     * if the parsing fails.
     *
     * @param input The string to parse.
     * @param consumer The consumer to call if the parsing fails.
     * @return The parsed argument as an {@link Argument}.
     */
    @Override
    public abstract Argument<T> parse(String input, Consumer<Exception> consumer);

    /**
     * Parses the given input string and applies the argument rules to the input.
     *
     * @param input The input to parse.
     * @throws ArgumentParseException If the result of applying any argument rule to the input is not valid.
     */
    public void parseRules(String input) {
        for (ArgumentRule<String, ?> rule : getArgumentRules()) {
            RuleData<?> result = rule.getRule().apply(new RuleData<>(input));
            ArgumentRuleResult ruleResult = rule.interpretResult(this, new RuleData<>(input), result);

            if (ruleResult.result() == ArgumentRuleResult.Result.FAILURE)
                throw onFail(ruleResult.info(), null);
        }
    }

    /**
     * Adds a new {@link ArgumentRule} to the argument.
     *
     * @param rule The argument rule to add.
     * @return The argument.
     */
    public CommandArgument<T> withRule(ArgumentRule<String, ?> rule) {
        this.addRule(rule);
        return this;
    }

    /**
     * Adds multiple {@link ArgumentRule}s to the argument.
     *
     * @param rules The argument rules to add.
     * @return The argument.
     */
    @SafeVarargs
    public final CommandArgument<?> withRules(ArgumentRule<String, ?>... rules) {
        for (ArgumentRule<String, ?> rule : rules)
            this.addRule(rule);
        return this;
    }

    /**
     * Adds a default {@link Argument} to the argument that will be returned if the argument is not present
     * or if the argument fails to parse.
     *
     * @param defaultValue The default value of the argument.
     * @return The argument.
     */
    public CommandArgument<T> withDefault(Argument<T> defaultValue) {
        this.setDefaultValue(defaultValue);
        return this;
    }

}
