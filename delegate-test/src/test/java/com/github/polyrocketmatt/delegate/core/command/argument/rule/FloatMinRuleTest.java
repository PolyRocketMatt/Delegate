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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FloatMinRuleTest {

    private final float min = 5.0f;
    @SuppressWarnings("FieldCanBeLocal") private final String validInclusiveInput = "5.0f";
    @SuppressWarnings("FieldCanBeLocal") private final String validExclusiveInput = "6.0f";
    @SuppressWarnings("FieldCanBeLocal") private final String invalidInput = "0.0f";

    @Test
    public void testPrimaryConstructor() {
        FloatMinRule rule = new FloatMinRule(min, false);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5.0f, rule.getMin());
        assertFalse(rule.isInclusive());
    }

    @Test
    public void testSecondaryConstructor() {
        FloatMinRule rule = new FloatMinRule(min);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5.0f, rule.getMin());
        assertTrue(rule.isInclusive());
    }

    @Test
    public void testInterpretValidResultInclusive() {
        FloatMinRule rule = new FloatMinRule(min);
        RuleData<Boolean> result = rule.getRule().apply(validInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed FloatMinRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultExclusive() {
        FloatMinRule rule = new FloatMinRule(min, false);
        RuleData<Boolean> result = rule.getRule().apply(validExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed FloatMinRule", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidExclusive() {
        FloatMinRule rule = new FloatMinRule(min, false);
        RuleData<Boolean> result = rule.getRule().apply(validInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number greater than the minimum: %s (min %s)".formatted(validInclusiveInput, min), ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidType() {
        FloatMinRule rule = new FloatMinRule(min);
        RuleData<String> result = new RuleData<>("failed");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Expected result of rule did not match", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidResult() {
        FloatMinRule rule = new FloatMinRule(min);
        RuleData<Boolean> result = rule.getRule().apply(invalidInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), invalidInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number greater than the minimum: %s (min %s)".formatted(invalidInput, min), ruleResult.info());
    }

    @Test
    public void testParseNonFloat() {
        FloatMinRule rule = new FloatMinRule(min);
        RuleData<Boolean> result = rule.getRule().apply("not a float");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number greater than the minimum: %s (min %s)".formatted("", min), ruleResult.info());
    }

}
