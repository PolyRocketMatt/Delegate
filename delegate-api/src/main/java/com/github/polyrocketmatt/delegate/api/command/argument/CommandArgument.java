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
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Represents a command argument of a specific type.
 *
 * @param <T> The type of the argument.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public abstract class CommandArgument<T> extends CommandAttribute implements Bufferable, ArgumentParser<T>, ArgumentType<T> {

    private final String argumentDescription;
    private final List<ArgumentRule<?>> argumentRules;
    private final Class<T> argumentType;
    private final boolean isOptional;

    private Argument<T> defaultValue;

    /**
     * Creates a new command argument with an identifier, description and
     * argument rules that must be met for the argument to be valid.
     *
     * @param identifier The identifier of the argument.
     * @param argumentDescription The description of the argument.
     * @param argumentType The class type of the argument.
     * @param isOptional Whether the argument is optional or not.
     * @param argumentRules The argument rules that must be met for the argument to be valid.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the argument description is null.
     * @throws IllegalArgumentException If the argument type is null.
     * @throws IllegalArgumentException If the default value is null.
     * @throws IllegalArgumentException If the argument rules are null.
     */
    public CommandArgument(@NotNull String identifier, @NotNull String argumentDescription, @NotNull Class<T> argumentType,
                           @NotNull Argument<T> defaultValue, boolean isOptional, @NotNull List<ArgumentRule<?>> argumentRules) {
        super(identifier);
        validate("argumentDescription", String.class, argumentDescription);
        validate("argumentType", Class.class, argumentType);
        validate("defaultValue", Argument.class, defaultValue);
        validate("argumentRules", List.class, argumentRules);
        argumentRules.forEach(rule -> validate("element", ArgumentRule.class, rule));

        this.argumentDescription = argumentDescription;
        this.argumentRules = argumentRules;
        this.argumentType = argumentType;
        this.isOptional = isOptional;
        this.defaultValue = defaultValue;
    }

    /**
     * Gets the description of the argument.
     *
     * @return The description of the argument.
     */
    public @NotNull String getArgumentDescription() {
        return argumentDescription;
    }

    /**
     * Gets the argument rules that must be met for the argument to be valid.
     *
     * @return The argument rules that must be met for the argument to be valid.
     */
    public @NotNull List<ArgumentRule<?>> getArgumentRules() {
        return argumentRules;
    }

    /**
     * Adds an argument rule to the argument.
     *
     * @param rule The argument rule to add.
     */
    private void addRule(@NotNull ArgumentRule<?> rule) {
        validate("rule", ArgumentRule.class, rule);

        this.argumentRules.add(rule);
    }

    /**
     * Gets the class type of the argument.
     *
     * @return The class type of the argument.
     */
    public @NotNull Class<T> getArgumentType() {
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
    public @NotNull Argument<T> getDefault() {
        return defaultValue;
    }

    /**
     * Sets the default value of the argument.
     *
     * @param defaultValue The default value of the argument.
     */
    private void setDefaultValue(@NotNull Argument<T> defaultValue) {
        validate("defaultValue", Argument.class, defaultValue);

        this.defaultValue = defaultValue;
    }


    /**
     * Parses the argument value from the given input and calls the given {@link Consumer}
     * if the parsing fails.
     *
     * @param input The string to parse.
     * @return The parsed argument as an {@link Argument}.
     */
    public abstract @NotNull Argument<T> parse(@Nullable String input) throws ArgumentParseException;

    /**
     * Parses the argument value from the given string reader and returns
     * the parsed value as an object of type {@link T} or throws an
     * exception if the parsing fails.
     *
     * @param reader The string reader to parse.
     * @return The parsed argument as an {@link Argument}.
     * @throws ArgumentParseException If the parsing fails.
     */
    //  TODO: Parse rules when parsing from string reader
    @Override
    public abstract @NotNull T parse(@NotNull StringReader reader) throws ArgumentParseException;

    /**
     * Parses the given input string and applies the argument rules to the input.
     *
     * @param input The input to parse.
     * @throws ArgumentParseException If the result of applying any argument rule to the input is not valid.
     */
    public void parseRules(@NotNull String input) {
        validate("input", String.class, input);

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
    public @NotNull CommandArgument<T> withRule(@NotNull ArgumentRule<?> rule) {
        validate("rule", ArgumentRule.class, rule);

        this.addRule(rule);
        return this;
    }

    /**
     * Adds multiple {@link ArgumentRule}s to the argument.
     *
     * @param rules The argument rules to add.
     * @return The argument.
     */
    public final @NotNull CommandArgument<?> withRules(@NotNull ArgumentRule<?>... rules) {
        for (ArgumentRule<?> rule : rules) {
            validate("rule", ArgumentRule.class, rule);
            this.addRule(rule);
        }
        return this;
    }

    /**
     * Adds a default {@link Argument} to the argument that will be returned if the argument is not present
     * or if the argument fails to parse.
     *
     * @param defaultValue The default value of the argument.
     * @return The argument.
     */
    public @NotNull CommandArgument<T> withDefault(@NotNull Argument<T> defaultValue) {
        validate("defaultValue", Argument.class, defaultValue);

        this.setDefaultValue(defaultValue);
        return this;
    }

    @Override
    public @NotNull AttributeType getType() {
        return AttributeType.ARGUMENT;
    }
}
