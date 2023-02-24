// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.argument;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.mojang.brigadier.StringReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;
import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;
import static org.junit.jupiter.api.Assertions.*;

public class CommandArgumentTest {

    private final String identifier = "testIdentifier";
    private final String argumentDescription = "testDescription";
    private final Class<String> argumentType = String.class;
    private final Argument<String> defaultValue = new Argument<>(newId(), "testDefault");
    private final List<ArgumentRule<?>> argumentRules = new ArrayList<>();

    private static class CommandArgumentImpl extends CommandArgument<String> {

        public CommandArgumentImpl(@NotNull String identifier, @NotNull String argumentDescription, @NotNull Class<String> argumentType,
                                   @NotNull Argument<String> defaultValue, boolean isOptional, @NotNull List<ArgumentRule<?>> argumentRules) {
            super(identifier, argumentDescription, argumentType, defaultValue, isOptional, argumentRules);
        }

        @Override
        public @NotNull Argument<String> parse(@Nullable String input) throws ArgumentParseException {
            return new Argument<>(getIdentifier(), (input == null) ? "" : input);
        }

        @Override
        public @NotNull String parse(@NotNull StringReader reader) throws ArgumentParseException {
            try {
                return reader.readString();
            } catch (Exception ex) {
                return "";
            }
        }

    }

    private final ArgumentRule<Boolean> ruleA = new ArgumentRule<>(input -> new RuleData<>(input.length() > 0)) {
        @Override
        public @NotNull ArgumentRuleResult interpretResult(@Nullable CommandArgument<?> argument, @Nullable String input, @NotNull RuleData<?> output) {
            validate("output", RuleData.class, output);

            if (!(output.value() instanceof Boolean result))
                return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Expected result of rule did not match");
            if (!result)
                return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Condition was not met for input: %s".formatted(input));
            return new ArgumentRuleResult(ArgumentRuleResult.Result.SUCCESS, "Successfully passed %s".formatted(getClass().getSimpleName()));
        }
    };

    private final ArgumentRule<Boolean> ruleB = new ArgumentRule<>(input -> new RuleData<>(input.length() < 10)) {
        @Override
        public @NotNull ArgumentRuleResult interpretResult(@Nullable CommandArgument<?> argument, @Nullable String input, @NotNull RuleData<?> output) {
            validate("output", RuleData.class, output);

            if (!(output.value() instanceof Boolean result))
                return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Expected result of rule did not match");
            if (!result)
                return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Condition was not met for input: %s".formatted(input));
            return new ArgumentRuleResult(ArgumentRuleResult.Result.SUCCESS, "Successfully passed %s".formatted(getClass().getSimpleName()));        }
    };

    @Test
    public void testConstructor() {
        CommandArgumentImpl argument = new CommandArgumentImpl(identifier, argumentDescription, argumentType,
                defaultValue, false, argumentRules);

        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(argumentDescription, argument.getArgumentDescription());
        assertEquals(argumentRules, argument.getArgumentRules());
        assertEquals(argumentType, argument.getArgumentType());

        assertFalse(argument.isOptional());
        assertTrue(argument.isRequired());

        assertEquals(defaultValue, argument.getDefault());
    }

    @Test
    public void testConstructorIllegal() {
        assertThrows(IllegalArgumentException.class, () -> new CommandArgumentImpl(null, argumentDescription, argumentType, defaultValue, false, argumentRules));
        assertThrows(IllegalArgumentException.class, () -> new CommandArgumentImpl(identifier, null, argumentType, defaultValue, false, argumentRules));
        assertThrows(IllegalArgumentException.class, () -> new CommandArgumentImpl(identifier, argumentDescription, null, defaultValue, false, argumentRules));
        assertThrows(IllegalArgumentException.class, () -> new CommandArgumentImpl(identifier, argumentDescription, argumentType, null, false, argumentRules));
        assertThrows(IllegalArgumentException.class, () -> new CommandArgumentImpl(identifier, argumentDescription, argumentType, defaultValue, false, null));
    }

    @Test
    public void testWithRule() {
        CommandArgumentImpl argument = new CommandArgumentImpl(identifier, argumentDescription, argumentType,
                defaultValue, false, argumentRules);

        assertEquals(0, argument.getArgumentRules().size());

        argument.withRule(ruleA);

        assertEquals(1, argument.getArgumentRules().size());
        assertEquals(ruleA, argument.getArgumentRules().get(0));
    }

    @Test
    public void testWithRules() {
        CommandArgumentImpl argument = new CommandArgumentImpl(identifier, argumentDescription, argumentType,
                defaultValue, false, argumentRules);

        assertEquals(0, argument.getArgumentRules().size());

        argument.withRules(ruleA, ruleB);

        assertEquals(2, argument.getArgumentRules().size());
        assertEquals(ruleA, argument.getArgumentRules().get(0));
        assertEquals(ruleB, argument.getArgumentRules().get(1));
    }

    @Test
    public void testWithDefault() {
        CommandArgumentImpl argument = new CommandArgumentImpl(identifier, argumentDescription, argumentType,
                defaultValue, false, argumentRules);
        Argument<String> newDefault = new Argument<>(newId(), "newDefault");

        assertEquals(defaultValue, argument.getDefault());
        assertEquals("testDefault", argument.getDefault().output());

        argument.withDefault(newDefault);

        assertEquals(newDefault, argument.getDefault());
        assertEquals("newDefault", argument.getDefault().output());
    }

    @Test
    public void testParseRules() {
        CommandArgumentImpl argument = new CommandArgumentImpl(identifier, argumentDescription, argumentType,
                defaultValue, false, argumentRules);

        argument.withRules(ruleA, ruleB);

        assertEquals(2, argument.getArgumentRules().size());
        assertDoesNotThrow(() -> argument.parseRules("test"));
        assertDoesNotThrow(() -> argument.parseRules("a"));
        assertDoesNotThrow(() -> argument.parseRules("abcdefghi"));
        assertThrows(ArgumentParseException.class, () -> argument.parseRules(""));
        assertThrows(ArgumentParseException.class, () -> argument.parseRules("abcdefghij"));
    }

}
