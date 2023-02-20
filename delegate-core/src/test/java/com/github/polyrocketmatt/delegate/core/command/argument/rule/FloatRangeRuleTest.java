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

public class FloatRangeRuleTest {

    private final float min = 5.0f;
    private final float max = 10.0f;
    @SuppressWarnings("FieldCanBeLocal") private final String validLeftInclusiveInput = "5.0f";
    @SuppressWarnings("FieldCanBeLocal") private final String validLeftExclusiveInput = "6.0f";
    @SuppressWarnings("FieldCanBeLocal") private final String validRightInclusiveInput = "10.0f";
    @SuppressWarnings("FieldCanBeLocal") private final String validRightExclusiveInput = "9.0f";
    @SuppressWarnings("FieldCanBeLocal") private final String validBothInclusiveInput = "7.0f";
    @SuppressWarnings("FieldCanBeLocal") private final String validBothExclusiveInput = "8.0f";
    @SuppressWarnings("FieldCanBeLocal") private final String invalidLowerInput = "0.0f";
    @SuppressWarnings("FieldCanBeLocal") private final String invalidUpperInput = "15.0f";

    @Test
    public void testPrimaryConstructor() {
        FloatRangeRule rule = new FloatRangeRule(min, max, false, false);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5.0f, rule.getMin());
        assertEquals(10.0f, rule.getMax());
        assertFalse(rule.isLeftInclusive());
        assertFalse(rule.isRightInclusive());
    }

    @Test
    public void testSecondaryConstructor() {
        FloatRangeRule rule = new FloatRangeRule(min, max);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5.0f, rule.getMin());
        assertEquals(10.0f, rule.getMax());
        assertTrue(rule.isLeftInclusive());
        assertTrue(rule.isRightInclusive());
    }

    @Test
    public void testInterpretValidResultLeftInclusive() {
        FloatRangeRule rule = new FloatRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(validLeftInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validLeftInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed FloatRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultLeftExclusive() {
        FloatRangeRule rule = new FloatRangeRule(min, max, false, false);
        RuleData<Boolean> result = rule.getRule().apply(validLeftExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validLeftExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed FloatRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultRightInclusive() {
        FloatRangeRule rule = new FloatRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(validRightInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validRightInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed FloatRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultRightExclusive() {
        FloatRangeRule rule = new FloatRangeRule(min, max, false, false);
        RuleData<Boolean> result = rule.getRule().apply(validRightExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validRightExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed FloatRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultBothInclusive() {
        FloatRangeRule rule = new FloatRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(validBothInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validBothInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed FloatRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultBothExclusive() {
        FloatRangeRule rule = new FloatRangeRule(min, max, false, false);
        RuleData<Boolean> result = rule.getRule().apply(validBothExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validBothExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed FloatRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidType() {
        FloatRangeRule rule = new FloatRangeRule(min, max);
        RuleData<String> result = new RuleData<>("failed");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Expected result of rule did not match", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidLower() {
        FloatRangeRule rule = new FloatRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(invalidLowerInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), invalidLowerInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number inside the valid range: %s (min: %s, max: %s)".formatted(invalidLowerInput, min, max), ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidUpper() {
        FloatRangeRule rule = new FloatRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(invalidUpperInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), invalidUpperInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number inside the valid range: %s (min: %s, max: %s)".formatted(invalidUpperInput, min, max), ruleResult.info());
    }

    @Test
    public void testParseNonFloat() {
        FloatRangeRule rule = new FloatRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply("not a float");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number inside the valid range: %s (min: %s, max: %s)".formatted("", min, max), ruleResult.info());
    }

}
