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

public class StringMaxLengthRuleTest {

    private final int max = 5;
    @SuppressWarnings("FieldCanBeLocal") private final String validInclusiveInput = "abcde";
    @SuppressWarnings("FieldCanBeLocal") private final String validExclusiveInput = "abcd";
    @SuppressWarnings("FieldCanBeLocal") private final String invalidInput = "abcdef";

    @Test
    public void testPrimaryConstructor() {
        StringMaxLengthRule rule = new StringMaxLengthRule(max, false);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5, rule.getMaxLength());
        assertFalse(rule.isInclusive());
    }

    @Test
    public void testSecondaryConstructor() {
        StringMaxLengthRule rule = new StringMaxLengthRule(max);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5, rule.getMaxLength());
        assertTrue(rule.isInclusive());
    }

    @Test
    public void testInterpretValidResultInclusive() {
        StringMaxLengthRule rule = new StringMaxLengthRule(max);
        RuleData<Boolean> result = rule.getRule().apply(validInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed StringMaxLengthRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultExclusive() {
        StringMaxLengthRule rule = new StringMaxLengthRule(max, false);
        RuleData<Boolean> result = rule.getRule().apply(validExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed StringMaxLengthRule", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidExclusive() {
        StringMaxLengthRule rule = new StringMaxLengthRule(max, false);
        RuleData<Boolean> result = rule.getRule().apply(validInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("String length was null or not a number less than the maximum: %s (max %s)".formatted(validInclusiveInput, max), ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidType() {
        StringMaxLengthRule rule = new StringMaxLengthRule(max);
        RuleData<String> result = new RuleData<>("failed");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Expected result of rule did not match", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidResult() {
        StringMaxLengthRule rule = new StringMaxLengthRule(max);
        RuleData<Boolean> result = rule.getRule().apply(invalidInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), invalidInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("String length was null or not a number less than the maximum: %s (max %s)".formatted(invalidInput, max), ruleResult.info());
    }

}
