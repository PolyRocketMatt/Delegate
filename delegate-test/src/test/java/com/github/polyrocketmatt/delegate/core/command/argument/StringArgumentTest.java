// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringArgumentTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    @Test
    public void testPrimaryConstructor() {
        StringArgument argument = StringArgument.of("identifier", "description");

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(String.class, argument.getArgumentType());
    }

    @Test
    public void testSecondaryConstructor() {
        StringArgument argument = StringArgument.of("identifier", "description", "Hello, world!");

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(String.class, argument.getArgumentType());
        assertEquals("Hello, world!", argument.getDefault().output());
    }

    @Test
    public void testTernaryConstructor() {
        StringArgument argument = StringArgument.of("identifier", "description", new NonNullRule());

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(String.class, argument.getArgumentType());
        assertEquals(1, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof NonNullRule);
        assertNull(argument.getDefault().output());
    }

    @Test
    public void testQuaternaryConstructor() {
        StringArgument argument = StringArgument.of("identifier", "description", "Hello, world!", new NonNullRule());

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(String.class, argument.getArgumentType());
        assertEquals(1, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof NonNullRule);
        assertEquals("Hello, world!", argument.getDefault().output());
    }

    @Test
    public void testInternalParserSuccess() {
        StringArgument argument = StringArgument.of("identifier", "description", "Hello, world!");
        Argument<String> parsedArgumentA = argument.parse("\"0\"");
        Argument<String> parsedArgumentB = argument.parse("\"This is a string\"");
        Argument<String> parsedArgumentC = argument.parse("\"@#_Special_Char_!String01$^¨ù%´\"");
        Argument<String> parsedArgumentD = argument.parse("This is not enclosed");

        assertEquals("0", parsedArgumentA.output());
        assertEquals("This is a string", parsedArgumentB.output());
        assertEquals("@#_Special_Char_!String01$^¨ù%´", parsedArgumentC.output());
        assertEquals("This", parsedArgumentD.output());
    }

    @Test
    public void testInternalParserFailure() {
        StringArgument argument = StringArgument.of("identifier", "description");

        assertThrows(ArgumentParseException.class, () -> argument.parse((String) null));
    }

    @Test
    public void testInternalParserFailureWithDefault() {
        StringArgument argument = StringArgument.of("identifier", "description", "Hello, world!");
        Argument<String> parsedArgumentA = argument.parse((String) null);

        assertEquals("Hello, world!", parsedArgumentA.output());
    }

    @Test
    public void testBrigadierParserSuccess() {
        StringArgument argument = StringArgument.of("identifier", "description", "Hello, world!");
        StringReader readerA = new StringReader("\"0\"");
        StringReader readerB = new StringReader("\"This is a string\"");
        StringReader readerC = new StringReader("\"@#_Special_Char_!String01$^¨ù%´\"");
        StringReader readerD = new StringReader("This is not enclosed");

        assertEquals("0", argument.parse(readerA));
        assertEquals("This is a string", argument.parse(readerB));
        assertEquals("@#_Special_Char_!String01$^¨ù%´", argument.parse(readerC));
        assertEquals("This", argument.parse(readerD));
    }

    @Test
    public void testBrigadierParserFailure() {
        StringArgument argument = StringArgument.of("identifier", "description");
        StringReader readerA = new StringReader((String) null);

        assertThrows(ArgumentParseException.class, () -> argument.parse(readerA));
    }

    @Test
    public void testBrigadierParserFailureWithDefault() {
        StringArgument argument = StringArgument.of("identifier", "description", "Hello, world!");
        StringReader readerA = new StringReader((String) null);

        assertEquals("Hello, world!", argument.parse(readerA));
    }

}
