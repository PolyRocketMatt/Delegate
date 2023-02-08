// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.FloatMaxRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.FloatMinRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class FloatArgumentTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    @Test
    public void testPrimaryConstructor() {
        FloatArgument argument = FloatArgument.of("identifier", "description");

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Float.class, argument.getArgumentType());
    }

    @Test
    public void testSecondaryConstructor() {
        FloatArgument argument = FloatArgument.of("identifier", "description", 0.0f);

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Float.class, argument.getArgumentType());
        assertEquals(0.0f, argument.getDefault().output());
    }

    @Test
    public void testTernaryConstructor() {
        FloatArgument argument = FloatArgument.of("identifier", "description", new NonNullRule());

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Float.class, argument.getArgumentType());
        assertEquals(1, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof NonNullRule);
        assertNull(argument.getDefault().output());
    }

    @Test
    public void testQuaternaryConstructor() {
        FloatArgument argument = FloatArgument.of("identifier", "description", 0.0f,
                new FloatMinRule(0.0f), new FloatMaxRule(1.0f));

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Float.class, argument.getArgumentType());
        assertEquals(2, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof FloatMinRule);
        assertTrue(argument.getArgumentRules().get(1) instanceof FloatMaxRule);
        assertEquals(0.0f, argument.getDefault().output());
    }

}
