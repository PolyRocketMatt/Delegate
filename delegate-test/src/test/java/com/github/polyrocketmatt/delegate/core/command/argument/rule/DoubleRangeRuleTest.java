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

public class DoubleRangeRuleTest {

    private final double min = 5.0f;
    private final double max = 10.0f;
    @SuppressWarnings("FieldCanBeLocal") private final String validLeftInclusiveInput = "5.0";
    @SuppressWarnings("FieldCanBeLocal") private final String validLeftExclusiveInput = "6.0";
    @SuppressWarnings("FieldCanBeLocal") private final String validRightInclusiveInput = "10.0";
    @SuppressWarnings("FieldCanBeLocal") private final String validRightExclusiveInput = "9.0";
    @SuppressWarnings("FieldCanBeLocal") private final String validBothInclusiveInput = "7.0";
    @SuppressWarnings("FieldCanBeLocal") private final String validBothExclusiveInput = "8.0";
    @SuppressWarnings("FieldCanBeLocal") private final String invalidLowerInput = "0.0";
    @SuppressWarnings("FieldCanBeLocal") private final String invalidUpperInput = "15.0";

    @Test
    public void testPrimaryConstructor() {
        DoubleRangeRule rule = new DoubleRangeRule(min, max, false, false);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5.0, rule.getMin());
        assertEquals(10.0, rule.getMax());
        assertFalse(rule.isLeftInclusive());
        assertFalse(rule.isRightInclusive());
    }

    @Test
    public void testSecondaryConstructor() {
        DoubleRangeRule rule = new DoubleRangeRule(min, max);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5.0, rule.getMin());
        assertEquals(10.0, rule.getMax());
        assertTrue(rule.isLeftInclusive());
        assertTrue(rule.isRightInclusive());
    }

    @Test
    public void testInterpretValidResultLeftInclusive() {
        DoubleRangeRule rule = new DoubleRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(validLeftInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validLeftInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed DoubleRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultLeftExclusive() {
        DoubleRangeRule rule = new DoubleRangeRule(min, max, false, false);
        RuleData<Boolean> result = rule.getRule().apply(validLeftExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validLeftExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed DoubleRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultRightInclusive() {
        DoubleRangeRule rule = new DoubleRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(validRightInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validRightInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed DoubleRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultRightExclusive() {
        DoubleRangeRule rule = new DoubleRangeRule(min, max, false, false);
        RuleData<Boolean> result = rule.getRule().apply(validRightExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validRightExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed DoubleRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultBothInclusive() {
        DoubleRangeRule rule = new DoubleRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(validBothInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validBothInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed DoubleRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultBothExclusive() {
        DoubleRangeRule rule = new DoubleRangeRule(min, max, false, false);
        RuleData<Boolean> result = rule.getRule().apply(validBothExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validBothExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed DoubleRangeRule", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidType() {
        DoubleRangeRule rule = new DoubleRangeRule(min, max);
        RuleData<String> result = new RuleData<>("failed");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Expected result of rule did not match", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidLower() {
        DoubleRangeRule rule = new DoubleRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(invalidLowerInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), invalidLowerInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number inside the valid range: %s (min: %s, max: %s)".formatted(invalidLowerInput, min, max), ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidUpper() {
        DoubleRangeRule rule = new DoubleRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply(invalidUpperInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), invalidUpperInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number inside the valid range: %s (min: %s, max: %s)".formatted(invalidUpperInput, min, max), ruleResult.info());
    }

    @Test
    public void testParseNonFloat() {
        DoubleRangeRule rule = new DoubleRangeRule(min, max);
        RuleData<Boolean> result = rule.getRule().apply("not a double");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number inside the valid range: %s (min: %s, max: %s)".formatted("", min, max), ruleResult.info());
    }

}
