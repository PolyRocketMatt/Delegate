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

    @Test
    public void testInternalParserSuccess() {
        FloatArgument argument = FloatArgument.of("identifier", "description", 0.0f);
        Argument<Float> parsedArgumentA = argument.parse("0.0");
        Argument<Float> parsedArgumentB = argument.parse("100");

        assertEquals(0.0f, parsedArgumentA.output());
        assertEquals(100.0f, parsedArgumentB.output());
    }

    @Test
    public void testInternalParserFailure() {
        FloatArgument argument = FloatArgument.of("identifier", "description");

        assertThrows(ArgumentParseException.class, () -> argument.parse("0.0ff"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("not a number"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0.0.0"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0_0"));
    }

    @Test
    public void testInternalParserFailureWithDefault() {
        FloatArgument argument = FloatArgument.of("identifier", "description", 0.0f);
        Argument<Float> parsedArgumentA = argument.parse("0.0ff");
        Argument<Float> parsedArgumentB = argument.parse("not a number");
        Argument<Float> parsedArgumentC = argument.parse("0.0.0");
        Argument<Float> parsedArgumentD = argument.parse("0_0");

        assertEquals(0.0f, parsedArgumentA.output());
        assertEquals(0.0f, parsedArgumentB.output());
        assertEquals(0.0f, parsedArgumentC.output());
        assertEquals(0.0f, parsedArgumentD.output());
    }

    @Test
    public void testBrigadierParserSuccess() {
        FloatArgument argument = FloatArgument.of("identifier", "description", 0.0f);
        StringReader reader = new StringReader("0.0f");

        assertEquals(0.0f, argument.parse(reader));
    }

    @Test
    public void testBrigadierParserFailure() {
        FloatArgument argument = FloatArgument.of("identifier", "description");
        StringReader readerA = new StringReader("not a number");
        StringReader readerB = new StringReader("0.0.0");

        assertThrows(ArgumentParseException.class, () -> argument.parse(readerA));
        assertThrows(ArgumentParseException.class, () -> argument.parse(readerB));
    }

    @Test
    public void testBrigadierParserFailureWithDefault() {
        FloatArgument argument = FloatArgument.of("identifier", "description", 0.0f);
        StringReader readerA = new StringReader("not a number");
        StringReader readerB = new StringReader("0.0.0");

        assertEquals(0.0f, argument.parse(readerA));
        assertEquals(0.0f, argument.parse(readerB));
    }

}
