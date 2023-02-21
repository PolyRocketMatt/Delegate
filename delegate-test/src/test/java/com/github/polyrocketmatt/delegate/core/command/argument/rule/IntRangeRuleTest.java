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

public class IntRangeRuleTest {

    private final int min = 5;
    private final int max = 10;
    @SuppressWarnings("FieldCanBeLocal") private final String validLeftInclusiveInput = "5";
    @SuppressWarnings("FieldCanBeLocal") private final String validLeftExclusiveInput = "6";
    @SuppressWarnings("FieldCanBeLocal") private final String validRightInclusiveInput = "10";
    @SuppressWarnings("FieldCanBeLocal") private final String validRightExclusiveInput = "9";
    @SuppressWarnings("FieldCanBeLocal") private final String validBothInclusiveInput = "7";
    @SuppressWarnings("FieldCanBeLocal") private final String validBothExclusiveInput = "8";
    @SuppressWarnings("FieldCanBeLocal") private final String invalidLowerInput = "0";
    @SuppressWarnings("FieldCanBeLocal") private final String invalidUpperInput = "15";

    @Test
    public void testPrimaryConstructor() {
        IntRangeRule rule = new IntRangeRule(min, max, false, false);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5, rule.getMin());
        assertEquals(10, rule.getMax());
        assertFalse(rule.isLeftInclusive());
        assertFalse(rule.isRightInclusive());
    }

    @Test
    public void testSecondaryConstructor() {
        IntRangeRule rule = new IntRangeRule(min, max);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5, rule.getMin());
        assertEquals(10, rule.getMax());
        assertTrue(rule.isLeftInclusive());
        assertTrue(rule.isRightInclusive());
    }

    @Test
    public void testInterpretValidResultLeftInclusive() {
        IntRangeRule rule = new IntRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(validLeftInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validLeftInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed IntRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultLeftExclusive() {
        IntRangeRule rule = new IntRangeRule(min, max, false, false);
        RuleData<Boolean> result = rule.getRule().apply(validLeftExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validLeftExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed IntRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultRightInclusive() {
        IntRangeRule rule = new IntRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(validRightInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validRightInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed IntRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultRightExclusive() {
        IntRangeRule rule = new IntRangeRule(min, max, false, false);
        RuleData<Boolean> result = rule.getRule().apply(validRightExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validRightExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed IntRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultBothInclusive() {
        IntRangeRule rule = new IntRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(validBothInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validBothInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed IntRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultBothExclusive() {
        IntRangeRule rule = new IntRangeRule(min, max, false, false);
        RuleData<Boolean> result = rule.getRule().apply(validBothExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validBothExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed IntRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidType() {
        IntRangeRule rule = new IntRangeRule(min, max);
        RuleData<String> result = new RuleData<>("failed");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Expected result of rule did not match", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidLower() {
        IntRangeRule rule = new IntRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(invalidLowerInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), invalidLowerInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number inside the valid range: %s (min: %s, max: %s)".formatted(invalidLowerInput, min, max), ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidUpper() {
        IntRangeRule rule = new IntRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(invalidUpperInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), invalidUpperInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number inside the valid range: %s (min: %s, max: %s)".formatted(invalidUpperInput, min, max), ruleResult.info());
    }

    @Test
    public void testParseNonInteger() {
        IntRangeRule rule = new IntRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply("not an integer");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number inside the valid range: %s (min: %s, max: %s)".formatted("", min, max), ruleResult.info());
    }

}
