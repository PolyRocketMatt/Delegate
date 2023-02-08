// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.argument;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.Bufferable;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

/**
 * Represents a command argument of a specific type.
 *
 * @param <T> The type of the argument.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public abstract class CommandArgument<T> extends CommandAttribute implements Bufferable, ArgumentParser<T>, ArgumentType<T> {

    private final String argumentDescription;
    private final List<ArgumentRule<?>> argumentRules;
    private final Class<T> argumentType;
    private final boolean isOptional;

    private Argument<T> defaultValue;

    /**
     * Creates a new command argument with an identifier and description.
     *
     * @param argumentDescription The description of the argument.
     * @param argumentType The class type of the argument.
     */
    public CommandArgument(String argumentDescription, Class<T> argumentType) {
        super(newId());
        this.argumentDescription = argumentDescription;
        this.argumentRules = List.of();
        this.argumentType = argumentType;
        this.isOptional = false;
        this.defaultValue = null;
    }

    /**
     * Creates a new command argument with an identifier and description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param argumentType The class type of the argument.
     */
    public CommandArgument(String identifier, String argumentDescription, Class<T> argumentType) {
        super(identifier);
        this.argumentDescription = argumentDescription;
        this.argumentRules = List.of();
        this.argumentType = argumentType;
        this.isOptional = false;
        this.defaultValue = null;
    }

    /**
     * Creates a new command argument with an identifier, description and
     * argument rules that must be met for the argument to be valid.
     *
     * @param argumentDescription The description of the argument.
     * @param argumentType The class type of the argument.
     * @param argumentRule The argument rule that must be met for the argument to be valid.
     */
    public CommandArgument(String argumentDescription, Class<T> argumentType, ArgumentRule<?> argumentRule) {
        super(newId());
        this.argumentDescription = argumentDescription;
        this.argumentRules = List.of(argumentRule);
        this.argumentType = argumentType;
        this.isOptional = false;
        this.defaultValue = null;
    }

    /**
     * Creates a new command argument with an identifier, description and
     * argument rules that must be met for the argument to be valid.
     *
     * @param argumentDescription The description of the argument.
     * @param argumentType The class type of the argument.
     * @param argumentRules The argument rules that must be met for the argument to be valid.
     */
    public CommandArgument(String argumentDescription, Class<T> argumentType, List<ArgumentRule<?>> argumentRules) {
        super(newId());
        this.argumentDescription = argumentDescription;
        this.argumentRules = argumentRules;
        this.argumentType = argumentType;
        this.isOptional = false;
        this.defaultValue = null;
    }

    /**
     * Creates a new command argument with an identifier, description and
     * argument rules that must be met for the argument to be valid.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param argumentType The class type of the argument.
     * @param argumentRule The argument rule that must be met for the argument to be valid.
     */
    public CommandArgument(String identifier, String argumentDescription, Class<T> argumentType, ArgumentRule<?> argumentRule) {
        super(identifier);
        this.argumentDescription = argumentDescription;
        this.argumentRules = List.of(argumentRule);
        this.argumentType = argumentType;
        this.isOptional = false;
        this.defaultValue = null;
    }

    /**
     * Creates a new command argument with an identifier, description and
     * argument rules that must be met for the argument to be valid.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param argumentType The class type of the argument.
     * @param argumentRules The argument rules that must be met for the argument to be valid.
     */
    public CommandArgument(String identifier, String argumentDescription, Class<T> argumentType, List<ArgumentRule<?>> argumentRules) {
        super(identifier);
        this.argumentDescription = argumentDescription;
        this.argumentRules = argumentRules;
        this.argumentType = argumentType;
        this.isOptional = false;
        this.defaultValue = null;
    }

    /**
     * Creates a new command argument with an identifier and description.
     *
     * @param argumentDescription The description of the argument.
     * @param argumentType The class type of the argument.
     * @param isOptional Whether the argument is optional or not.
     */
    public CommandArgument(String argumentDescription, Class<T> argumentType, boolean isOptional) {
        super(newId());
        this.argumentDescription = argumentDescription;
        this.argumentRules = List.of();
        this.argumentType = argumentType;
        this.isOptional = isOptional;
        this.defaultValue = null;
    }

    /**
     * Creates a new command argument with an identifier and description.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param argumentType The class type of the argument.
     * @param isOptional Whether the argument is optional or not.
     */
    public CommandArgument(String identifier, String argumentDescription, Class<T> argumentType, boolean isOptional) {
        super(identifier);
        this.argumentDescription = argumentDescription;
        this.argumentRules = List.of();
        this.argumentType = argumentType;
        this.isOptional = isOptional;
        this.defaultValue = null;
    }

    /**
     * Creates a new command argument with an identifier and description.
     *
     * @param argumentDescription The description of the argument.
     * @param argumentType The class type of the argument.
     * @param isOptional Whether the argument is optional or not.
     * @param argumentRule The argument rule that must be met for the argument to be valid.
     */
    public CommandArgument(String argumentDescription, Class<T> argumentType, boolean isOptional, ArgumentRule<?> argumentRule) {
        super(newId());
        this.argumentDescription = argumentDescription;
        this.argumentRules = List.of(argumentRule);
        this.argumentType = argumentType;
        this.isOptional = isOptional;
        this.defaultValue = null;
    }

    /**
     * Creates a new command argument with an identifier and description.
     *
     * @param argumentDescription The description of the argument.
     * @param argumentType The class type of the argument.
     * @param isOptional Whether the argument is optional or not.
     * @param argumentRules The argument rules that must be met for the argument to be valid.
     */
    public CommandArgument(String argumentDescription, Class<T> argumentType, boolean isOptional, List<ArgumentRule<?>> argumentRules) {
        super(newId());
        this.argumentDescription = argumentDescription;
        this.argumentRules = argumentRules;
        this.argumentType = argumentType;
        this.isOptional = isOptional;
        this.defaultValue = null;
    }

    /**
     * Creates a new command argument with an identifier, description and
     * argument rules that must be met for the argument to be valid.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param argumentType The class type of the argument.
     * @param isOptional Whether the argument is optional or not.
     * @param argumentRule The argument rule that must be met for the argument to be valid.
     */
    public CommandArgument(String identifier, String argumentDescription, Class<T> argumentType, boolean isOptional, ArgumentRule<?> argumentRule) {
        super(identifier);
        this.argumentDescription = argumentDescription;
        this.argumentRules = List.of(argumentRule);
        this.argumentType = argumentType;
        this.isOptional = isOptional;
        this.defaultValue = null;
    }

    /**
     * Creates a new command argument with an identifier, description and
     * argument rules that must be met for the argument to be valid.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param argumentType The class type of the argument.
     * @param isOptional Whether the argument is optional or not.
     * @param argumentRules The argument rules that must be met for the argument to be valid.
     */
    public CommandArgument(String identifier, String argumentDescription, Class<T> argumentType, boolean isOptional, List<ArgumentRule<?>> argumentRules) {
        super(identifier);
        this.argumentDescription = argumentDescription;
        this.argumentRules = argumentRules;
        this.argumentType = argumentType;
        this.isOptional = isOptional;
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
    public List<ArgumentRule<?>> getArgumentRules() {
        return argumentRules;
    }

    /**
     * Adds an argument rule to the argument.
     *
     * @param rule The argument rule to add.
     */
    private void addRule(ArgumentRule<?> rule) {
        this.argumentRules.add(rule);
    }

    /**
     * Gets the class type of the argument.
     *
     * @return The class type of the argument.
     */
    public Class<T> getArgumentType() {
        return argumentType;
    }

    /**
     * Gets whether the argument is optional or not.
     *
     * @return Whether the argument is optional or not.
     */
    public boolean isOptional() {
        return isOptional;
    }

    /**
     * Gets whether the argument is required or not.
     *
     * @return Whether the argument is required or not.
     */
    public boolean isRequired() { return !isOptional; }

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
    public Argument<T> parse(String input) throws ArgumentParseException {
        return this.parse(input, ex -> { throw onFail(input, ex, argumentType); });
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
    public abstract Argument<T> parse(String input, Consumer<Exception> consumer) throws ArgumentParseException;

    /**
     * Parses the argument value from the given string reader and returns
     * the parsed value as an object of type {@link T} or throws an
     * exception if the parsing fails.
     *
     * @param reader The string reader to parse.
     * @return The parsed argument as an {@link Argument}.
     * @throws CommandSyntaxException If the parsing fails.
     */
    //  TODO: Parse rules when parsing from string reader
    @Override
    public abstract T parse(StringReader reader) throws CommandSyntaxException;

    /**
     * Parses the given input string and applies the argument rules to the input.
     *
     * @param input The input to parse.
     * @throws ArgumentParseException If the result of applying any argument rule to the input is not valid.
     */
    public void parseRules(String input) {
        for (ArgumentRule<?> rule : getArgumentRules()) {
            RuleData<?> result = rule.getRule().apply(input);
            ArgumentRuleResult ruleResult = rule.interpretResult(this, input, result);

            if (ruleResult.result() == ArgumentRuleResult.Result.FAILURE)
                throw onFail(ruleResult.info(), null, argumentType);
        }
    }

    /**
     * Adds a new {@link ArgumentRule} to the argument.
     *
     * @param rule The argument rule to add.
     * @return The argument.
     */
    public CommandArgument<T> withRule(ArgumentRule<?> rule) {
        this.addRule(rule);
        return this;
    }

    /**
     * Adds multiple {@link ArgumentRule}s to the argument.
     *
     * @param rules The argument rules to add.
     * @return The argument.
     */
    public final CommandArgument<?> withRules(ArgumentRule<?>... rules) {
        for (ArgumentRule<?> rule : rules)
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

    @Override
    public AttributeType getType() {
        return AttributeType.ARGUMENT;
    }
}
