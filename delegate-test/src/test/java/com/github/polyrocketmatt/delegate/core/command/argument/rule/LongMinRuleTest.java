package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;
import com.github.polyrocketmatt.delegate.core.command.argument.StringArgument;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongMinRuleTest {

    private final long min = 5L;
    @SuppressWarnings("FieldCanBeLocal") private final String validInclusiveInput = "5";
    @SuppressWarnings("FieldCanBeLocal") private final String validExclusiveInput = "6";
    @SuppressWarnings("FieldCanBeLocal") private final String invalidInput = "0";

    @Test
    public void testPrimaryConstructor() {
        LongMinRule rule = new LongMinRule(min, false);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5L, rule.getMin());
        assertFalse(rule.isInclusive());
    }

    @Test
    public void testSecondaryConstructor() {
        LongMinRule rule = new LongMinRule(min);

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);

        assertEquals(5L, rule.getMin());
        assertTrue(rule.isInclusive());
    }

    @Test
    public void testInterpretValidResultInclusive() {
        LongMinRule rule = new LongMinRule(min);
        RuleData<Boolean> result = rule.getRule().apply(validInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed LongMinRule", ruleResult.info());
    }

    @Test
    public void testInterpretValidResultExclusive() {
        LongMinRule rule = new LongMinRule(min, false);
        RuleData<Boolean> result = rule.getRule().apply(validExclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validExclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed LongMinRule", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidExclusive() {
        LongMinRule rule = new LongMinRule(min, false);
        RuleData<Boolean> result = rule.getRule().apply(validInclusiveInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), validInclusiveInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number greater than the minimum: %s (min %s)".formatted(validInclusiveInput, min), ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidType() {
        LongMinRule rule = new LongMinRule(min);
        RuleData<String> result = new RuleData<>("failed");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Expected result of rule did not match", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidResult() {
        LongMinRule rule = new LongMinRule(min);
        RuleData<Boolean> result = rule.getRule().apply(invalidInput);
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), invalidInput, result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number greater than the minimum: %s (min %s)".formatted(invalidInput, min), ruleResult.info());
    }

    @Test
    public void testParseNonInteger() {
        LongMinRule rule = new LongMinRule(min);
        RuleData<Boolean> result = rule.getRule().apply("not an integer");
        ArgumentRuleResult ruleResult = rule.interpretResult(StringArgument.of(UUID.randomUUID().toString(), ""), "", result);

        //  Assert result
        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Value was null or not a number greater than the minimum: %s (min %s)".formatted("", min), ruleResult.info());
    }

}
