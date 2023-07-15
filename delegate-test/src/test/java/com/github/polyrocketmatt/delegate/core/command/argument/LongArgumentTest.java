// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.LongMaxRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.LongMinRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongArgumentTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    @Test
    public void testPrimaryConstructor() {
        LongArgument argument = LongArgument.of("identifier", "description");

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Long.class, argument.getArgumentType());
    }

    @Test
    public void testSecondaryConstructor() {
        LongArgument argument = LongArgument.of("identifier", "description", 0);

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Long.class, argument.getArgumentType());
        assertEquals(0L, argument.getDefault().output());
    }

    @Test
    public void testTernaryConstructor() {
        LongArgument argument = LongArgument.of("identifier", "description", new NonNullRule());

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Long.class, argument.getArgumentType());
        assertEquals(1L, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof NonNullRule);
        assertNull(argument.getDefault().output());
    }

    @Test
    public void testQuaternaryConstructor() {
        LongArgument argument = LongArgument.of("identifier", "description", 0L,
                new LongMinRule(0), new LongMaxRule(1));

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Long.class, argument.getArgumentType());
        assertEquals(2, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof LongMinRule);
        assertTrue(argument.getArgumentRules().get(1) instanceof LongMaxRule);
        assertEquals(0L, argument.getDefault().output());
    }

    @Test
    public void testInternalParserSuccess() {
        LongArgument argument = LongArgument.of("identifier", "description", 0L);
        Argument<Long> parsedArgumentA = argument.parse("0");
        Argument<Long> parsedArgumentB = argument.parse("100");

        assertEquals(0L, parsedArgumentA.output());
        assertEquals(100L, parsedArgumentB.output());
    }

    @Test
    public void testInternalParserInputNull() {
        LongArgument argumentA = LongArgument.of("identifier", "description");
        LongArgument argumentB = LongArgument.of("identifier", "description", 1L);

        assertThrows(ArgumentParseException.class, () -> argumentA.parse((String) null));
        assertEquals(1L, argumentB.parse((String) null).output());
    }


    @Test
    public void testInternalParserFailure() {
        LongArgument argument = LongArgument.of("identifier", "description");

        assertThrows(ArgumentParseException.class, () -> argument.parse("0."));
        assertThrows(ArgumentParseException.class, () -> argument.parse("not a number"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0_0"));
    }

    @Test
    public void testInternalParserFailureWithDefault() {
        LongArgument argument = LongArgument.of("identifier", "description", 0L);
        Argument<Long> parsedArgumentA = argument.parse("0.");
        Argument<Long> parsedArgumentB = argument.parse("not a number");
        Argument<Long> parsedArgumentC = argument.parse("0_0");

        assertEquals(0L, parsedArgumentA.output());
        assertEquals(0L, parsedArgumentB.output());
        assertEquals(0L, parsedArgumentC.output());
    }

    @Test
    public void testBrigadierParserSuccess() {
        LongArgument argument = LongArgument.of("identifier", "description", 0);
        StringReader reader = new StringReader("0");

        assertEquals(0, argument.parse(reader));
    }

    @Test
    public void testBrigadierParserFailure() {
        LongArgument argument = LongArgument.of("identifier", "description");
        StringReader readerA = new StringReader("not a number");
        StringReader readerB = new StringReader("0.0.0");

        assertThrows(ArgumentParseException.class, () -> argument.parse(readerA));
        assertThrows(ArgumentParseException.class, () -> argument.parse(readerB));
    }

    @Test
    public void testBrigadierParserFailureWithDefault() {
        LongArgument argument = LongArgument.of("identifier", "description", 0L);
        StringReader readerA = new StringReader("not a number");
        StringReader readerB = new StringReader("0.0.0");

        assertEquals(0L, argument.parse(readerA));
        assertEquals(0L, argument.parse(readerB));
    }

}
