// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NonNullRuleTest {

    @Test
    public void testConstructor() {
        NonNullRule rule = new NonNullRule();

        //  Assert function
        assertEquals(1, rule.getRule().getClass().getInterfaces().length);
        assertEquals(Function.class, rule.getRule().getClass().getInterfaces()[0]);
    }

    @Test
    public void testInterpretResultNonNull() {
        NonNullRule rule = new NonNullRule();
        RuleData<Boolean> result = rule.getRule().apply("test");
        ArgumentRuleResult ruleResult = rule.interpretResult(null, "test", result);

        assertEquals(ArgumentRuleResult.Result.SUCCESS, ruleResult.result());
        assertEquals("Successfully passed NonNullRule", ruleResult.info());
    }

    @Test
    public void testInterpretResultNull() {
        NonNullRule rule = new NonNullRule();
        RuleData<Boolean> result = rule.getRule().apply(null);
        ArgumentRuleResult ruleResult = rule.interpretResult(null, null, result);

        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Non-nullity of value was not met", ruleResult.info());
    }

    @Test
    public void testInterpretResultInvalidType() {
        NonNullRule rule = new NonNullRule();
        RuleData<String> result = new RuleData<>("not a boolean");
        ArgumentRuleResult ruleResult = rule.interpretResult(null, "1", result);

        assertEquals(ArgumentRuleResult.Result.FAILURE, ruleResult.result());
        assertEquals("Expected result of rule did not match", ruleResult.info());
    }

}
