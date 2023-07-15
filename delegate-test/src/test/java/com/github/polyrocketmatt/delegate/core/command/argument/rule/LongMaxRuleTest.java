package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;
import com.github.polyrocketmatt.delegate.core.command.argument.StringArgument;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongMaxRuleTest {

    private final long max = 5L;
    @SuppressWarnings("FieldCanBeLocal") private final String validInclusiveInput = "5";
    @SuppressWarnings("FieldCanBeLocal") private final String validExclusiveInput = "4";
    @SuppressWarnings("FieldCanBeLocal") private final String invalidInput = "10";

    @Test
    public void testPrimaryConstructor() {
        LongMaxRule rule = new LongMaxRule(max, false);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5L, rule.getMax());
        assertFalse(rule.isInclusive());
    }

    @Test
    public void testSecondaryConstructor() {
        LongMaxRule rule = new LongMaxRule(max);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5L, rule.getMax());
        assertTrue(rule.isInclusive());
    }

    @Test
    public void testInterpretValidResultInclusive() {
        LongMaxRule rule = new LongMaxRule(max);
        RuleData<Boolean> result = rule.getRule().apply(validInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed LongMaxRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultExclusive() {
        LongMaxRule rule = new LongMaxRule(max, false);
        RuleData<Boolean> result = rule.getRule().apply(validExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed LongMaxRule", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidExclusive() {
        LongMaxRule rule = new LongMaxRule(max, false);
        RuleData<Boolean> result = rule.getRule().apply(validInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number less than the maximum: %s (max %s)".formatted(validInclusiveInput, max), ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidType() {
        LongMaxRule rule = new LongMaxRule(max);
        RuleData<String> result = new RuleData<>("failed");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Expected result of rule did not match", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidResult() {
        LongMaxRule rule = new LongMaxRule(max);
        RuleData<Boolean> result = rule.getRule().apply(invalidInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), invalidInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number less than the maximum: %s (max %s)".formatted(invalidInput, max), ruleResult.info());
    }

    @Test
    public void testParseNonInteger() {
        LongMaxRule rule = new LongMaxRule(max);
        RuleData<Boolean> result = rule.getRule().apply("not an integer");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number less than the maximum: %s (max %s)".formatted("", max), ruleResult.info());
    }

}
