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

public class StringMinLengthRuleTest {

    private final int min = 5;
    @SuppressWarnings("FieldCanBeLocal") private final String validInclusiveInput = "abcde";
    @SuppressWarnings("FieldCanBeLocal") private final String validExclusiveInput = "abcdef";
    @SuppressWarnings("FieldCanBeLocal") private final String invalidInput = "abcd";

    @Test
    public void testPrimaryConstructor() {
        StringMinLengthRule rule = new StringMinLengthRule(min, false);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5, rule.getMinLength());
        assertFalse(rule.isInclusive());
    }

    @Test
    public void testSecondaryConstructor() {
        StringMinLengthRule rule = new StringMinLengthRule(min);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5, rule.getMinLength());
        assertTrue(rule.isInclusive());
    }

    @Test
    public void testInterpretValidResultInclusive() {
        StringMinLengthRule rule = new StringMinLengthRule(min);
        RuleData<Boolean> result = rule.getRule().apply(validInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed StringMinLengthRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultExclusive() {
        StringMinLengthRule rule = new StringMinLengthRule(min, false);
        RuleData<Boolean> result = rule.getRule().apply(validExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed StringMinLengthRule", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidExclusive() {
        StringMinLengthRule rule = new StringMinLengthRule(min, false);
        RuleData<Boolean> result = rule.getRule().apply(validInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("String length was null or not a number greater than the minimum: %s (min %s)".formatted(validInclusiveInput, min), ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidType() {
        StringMinLengthRule rule = new StringMinLengthRule(min);
        RuleData<String> result = new RuleData<>("failed");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Expected result of rule did not match", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidResult() {
        StringMinLengthRule rule = new StringMinLengthRule(min);
        RuleData<Boolean> result = rule.getRule().apply(invalidInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), invalidInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("String length was null or not a number greater than the minimum: %s (min %s)".formatted(invalidInput, min), ruleResult.info());
    }

}
