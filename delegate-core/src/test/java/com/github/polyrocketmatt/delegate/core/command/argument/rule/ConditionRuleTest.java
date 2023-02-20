// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;
import com.github.polyrocketmatt.delegate.core.command.argument.StringArgument;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionRuleTest {

    @SuppressWarnings("FieldCanBeLocal") private final String validInput = "test";
    @SuppressWarnings("FieldCanBeLocal") private final String invalidInput = "t";

    @Test
    public void testConstructor() {
        ConditionRule rule = new ConditionRule(input -> input.length() > 1);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);
    }

    @Test
    public void testInterpretValidResult() {
        ConditionRule rule = new ConditionRule(input -> input.length() > 1);
        RuleData<Boolean> result = rule.getRule().apply(validInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed ConditionRule", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidType() {
        ConditionRule rule = new ConditionRule(input -> input.length() > 1);
        RuleData<String> result = new RuleData<>("failed");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Expected result of rule did not match", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidResult() {
        ConditionRule rule = new ConditionRule(input -> input.length() > 1);
        RuleData<Boolean> result = rule.getRule().apply(invalidInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), invalidInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Condition was not met for input: %s".formatted(invalidInput), ruleResult.info());
    }

}
