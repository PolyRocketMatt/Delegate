// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.IntMaxRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.IntMinRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntArgumentTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    @Test
    public void testPrimaryConstructor() {
        IntArgument argument = IntArgument.of("identifier", "description");

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Integer.class, argument.getArgumentType());
    }

    @Test
    public void testSecondaryConstructor() {
        IntArgument argument = IntArgument.of("identifier", "description", 0);

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Integer.class, argument.getArgumentType());
        assertEquals(0, argument.getDefault().output());
    }

    @Test
    public void testTernaryConstructor() {
        IntArgument argument = IntArgument.of("identifier", "description", new NonNullRule());

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Integer.class, argument.getArgumentType());
        assertEquals(1, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof NonNullRule);
        assertNull(argument.getDefault().output());
    }

    @Test
    public void testQuaternaryConstructor() {
        IntArgument argument = IntArgument.of("identifier", "description", 0,
                new IntMinRule(0), new IntMaxRule(1));

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Integer.class, argument.getArgumentType());
        assertEquals(2, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof IntMinRule);
        assertTrue(argument.getArgumentRules().get(1) instanceof IntMaxRule);
        assertEquals(0, argument.getDefault().output());
    }

    @Test
    public void testInternalParserSuccess() {
        IntArgument argument = IntArgument.of("identifier", "description", 0);
        Argument<Integer> parsedArgumentA = argument.parse("0");
        Argument<Integer> parsedArgumentB = argument.parse("100");

        assertEquals(0, parsedArgumentA.output());
        assertEquals(100, parsedArgumentB.output());
    }

    @Test
    public void testInternalParserFailure() {
        IntArgument argument = IntArgument.of("identifier", "description");

        assertThrows(ArgumentParseException.class, () -> argument.parse("0."));
        assertThrows(ArgumentParseException.class, () -> argument.parse("not a number"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0_0"));
    }

    @Test
    public void testInternalParserFailureWithDefault() {
        IntArgument argument = IntArgument.of("identifier", "description", 0);
        Argument<Integer> parsedArgumentA = argument.parse("0.");
        Argument<Integer> parsedArgumentB = argument.parse("not a number");
        Argument<Integer> parsedArgumentC = argument.parse("0_0");

        assertEquals(0, parsedArgumentA.output());
        assertEquals(0, parsedArgumentB.output());
        assertEquals(0, parsedArgumentC.output());
    }

    @Test
    public void testBrigadierParserSuccess() {
        IntArgument argument = IntArgument.of("identifier", "description", 0);
        StringReader reader = new StringReader("0");

        assertEquals(0, argument.parse(reader));
    }

    @Test
    public void testBrigadierParserFailure() {
        IntArgument argument = IntArgument.of("identifier", "description");
        StringReader readerA = new StringReader("not a number");
        StringReader readerB = new StringReader("0.0.0");

        assertThrows(ArgumentParseException.class, () -> argument.parse(readerA));
        assertThrows(ArgumentParseException.class, () -> argument.parse(readerB));
    }

    @Test
    public void testBrigadierParserFailureWithDefault() {
        IntArgument argument = IntArgument.of("identifier", "description", 0);
        StringReader readerA = new StringReader("not a number");
        StringReader readerB = new StringReader("0.0.0");

        assertEquals(0, argument.parse(readerA));
        assertEquals(0, argument.parse(readerB));
    }

}
