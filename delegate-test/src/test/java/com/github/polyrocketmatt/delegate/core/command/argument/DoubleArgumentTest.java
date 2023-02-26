// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.FloatMaxRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.FloatMinRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class DoubleArgumentTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    @Test
    public void testPrimaryConstructor() {
        DoubleArgument argument = DoubleArgument.of("identifier", "description");

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Double.class, argument.getArgumentType());
    }

    @Test
    public void testSecondaryConstructor() {
        DoubleArgument argument = DoubleArgument.of("identifier", "description", 0.0f);

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Double.class, argument.getArgumentType());
        assertEquals(0.0, argument.getDefault().output());
    }

    @Test
    public void testTernaryConstructor() {
        DoubleArgument argument = DoubleArgument.of("identifier", "description", new NonNullRule());

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Double.class, argument.getArgumentType());
        assertEquals(1, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof NonNullRule);
        assertNull(argument.getDefault().output());
    }

    @Test
    public void testQuaternaryConstructor() {
        DoubleArgument argument = DoubleArgument.of("identifier", "description", 0.0f,
                new FloatMinRule(0.0f), new FloatMaxRule(1.0f));

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Double.class, argument.getArgumentType());
        assertEquals(2, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof FloatMinRule);
        assertTrue(argument.getArgumentRules().get(1) instanceof FloatMaxRule);
        assertEquals(0.0, argument.getDefault().output());
    }

    @Test
    public void testInternalParserSuccess() {
        DoubleArgument argument = DoubleArgument.of("identifier", "description", 0.0f);
        Argument<Double> parsedArgumentA = argument.parse("0.0");
        Argument<Double> parsedArgumentB = argument.parse("100");

        assertEquals(0.0, parsedArgumentA.output());
        assertEquals(100.0, parsedArgumentB.output());
    }

    @Test
    public void testInternalParserInputNull() {
        DoubleArgument argumentA = DoubleArgument.of("identifier", "description");
        DoubleArgument argumentB = DoubleArgument.of("identifier", "description", 1.0);

        assertThrows(ArgumentParseException.class, () -> argumentA.parse((String) null));
        assertEquals(1.0, argumentB.parse((String) null).output());
    }

    @Test
    public void testInternalParserFailure() {
        DoubleArgument argument = DoubleArgument.of("identifier", "description");

        assertThrows(ArgumentParseException.class, () -> argument.parse("0.0ff"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("not a number"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0.0.0"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0_0"));
    }

    @Test
    public void testInternalParserFailureWithDefault() {
        DoubleArgument argument = DoubleArgument.of("identifier", "description", 0.0);
        Argument<Double> parsedArgumentA = argument.parse("0.0ff");
        Argument<Double> parsedArgumentB = argument.parse("not a number");
        Argument<Double> parsedArgumentC = argument.parse("0.0.0");
        Argument<Double> parsedArgumentD = argument.parse("0_0");

        assertEquals(0.0, parsedArgumentA.output());
        assertEquals(0.0, parsedArgumentB.output());
        assertEquals(0.0, parsedArgumentC.output());
        assertEquals(0.0, parsedArgumentD.output());
    }

    @Test
    public void testBrigadierParserSuccess() {
        DoubleArgument argument = DoubleArgument.of("identifier", "description", 0.0);
        StringReader reader = new StringReader("0.0f");

        assertEquals(0.0, argument.parse(reader));
    }

    @Test
    public void testBrigadierParserFailure() {
        DoubleArgument argument = DoubleArgument.of("identifier", "description");
        StringReader readerA = new StringReader("not a number");
        StringReader readerB = new StringReader("0.0.0");

        assertThrows(ArgumentParseException.class, () -> argument.parse(readerA));
        assertThrows(ArgumentParseException.class, () -> argument.parse(readerB));
    }

    @Test
    public void testBrigadierParserFailureWithDefault() {
        DoubleArgument argument = DoubleArgument.of("identifier", "description", 0.0);
        StringReader readerA = new StringReader("not a number");
        StringReader readerB = new StringReader("0.0.0");

        assertEquals(0.0, argument.parse(readerA));
        assertEquals(0.0, argument.parse(readerB));
    }

}
