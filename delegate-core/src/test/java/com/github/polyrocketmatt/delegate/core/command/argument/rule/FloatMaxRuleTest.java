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

public class FloatMaxRuleTest {

    private final float max = 5.0f;
    @SuppressWarnings("FieldCanBeLocal") private final String validInclusiveInput = "5.0f";
    @SuppressWarnings("FieldCanBeLocal") private final String validExclusiveInput = "4.0f";
    @SuppressWarnings("FieldCanBeLocal") private final String invalidInput = "10.0f";

    @Test
    public void testPrimaryConstructor() {
        FloatMaxRule rule = new FloatMaxRule(max, false);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5.0f, rule.getMax());
        assertFalse(rule.isInclusive());
    }

    @Test
    public void testSecondaryConstructor() {
        FloatMaxRule rule = new FloatMaxRule(max);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5.0f, rule.getMax());
        assertTrue(rule.isInclusive());
    }

    @Test
    public void testInterpretValidResultInclusive() {
        FloatMaxRule rule = new FloatMaxRule(max);
        RuleData<Boolean> result = rule.getRule().apply(validInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed FloatMaxRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultExclusive() {
        FloatMaxRule rule = new FloatMaxRule(max, false);
        RuleData<Boolean> result = rule.getRule().apply(validExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed FloatMaxRule", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidExclusive() {
        FloatMaxRule rule = new FloatMaxRule(max, false);
        RuleData<Boolean> result = rule.getRule().apply(validInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number less than the maximum: %s (max %s)".formatted(validInclusiveInput, max), ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidType() {
        FloatMaxRule rule = new FloatMaxRule(max);
        RuleData<String> result = new RuleData<>("failed");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Expected result of rule did not match", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidResult() {
        FloatMaxRule rule = new FloatMaxRule(max);
        RuleData<Boolean> result = rule.getRule().apply(invalidInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), invalidInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number less than the maximum: %s (max %s)".formatted(invalidInput, max), ruleResult.info());
    }

    @Test
    public void testParseNonFloat() {
        FloatMaxRule rule = new FloatMaxRule(max);
        RuleData<Boolean> result = rule.getRule().apply("not a float");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number less than the maximum: %s (max %s)".formatted("", max), ruleResult.info());
    }

}
