package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.ConditionRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoolArgumentTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    @Test
    public void testPrimaryConstructor() {
        BoolArgument argument = BoolArgument.of("identifier", "description");

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Boolean.class, argument.getArgumentType());
    }

    @Test
    public void testSecondaryConstructor() {
        BoolArgument argument = BoolArgument.of("identifier", "description", false);

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Boolean.class, argument.getArgumentType());
        assertEquals(false, argument.getDefault().output());
    }

    @Test
    public void testTernaryConstructor() {
        BoolArgument argument = BoolArgument.of("identifier", "description", new NonNullRule());

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Boolean.class, argument.getArgumentType());
        assertEquals(1, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof NonNullRule);
        assertFalse(argument.getDefault().output());
    }

    @Test
    public void testQuaternaryConstructor() {
        BoolArgument argument = BoolArgument.of("identifier", "description", false,
                new ConditionRule(input -> input.equals("true")));

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(Boolean.class, argument.getArgumentType());
        assertEquals(1, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof ConditionRule);
        assertEquals(false, argument.getDefault().output());
    }

    @Test
    public void testInternalParserSuccess() {
        BoolArgument argument = BoolArgument.of("identifier", "description", false);
        Argument<Boolean> parsedArgumentA = argument.parse("false");
        Argument<Boolean> parsedArgumentB = argument.parse("true");

        assertEquals(false, parsedArgumentA.output());
        assertEquals(true, parsedArgumentB.output());
    }

    @Test
    public void testInternalParserInputNull() {
        BoolArgument argumentA = BoolArgument.of("identifier", "description");
        BoolArgument argumentB = BoolArgument.of("identifier", "description", true);

        assertEquals(false, argumentA.parse((String) null).output());
        assertEquals(true, argumentB.parse((String) null).output());
    }

    @Test
    public void testInternalParserFailureWithDefault() {
        BoolArgument argument = BoolArgument.of("identifier", "description", false);
        Argument<Boolean> parsedArgumentA = argument.parse("tr.ue");
        Argument<Boolean> parsedArgumentB = argument.parse("ttrue");
        Argument<Boolean> parsedArgumentC = argument.parse("tru e");
        Argument<Boolean> parsedArgumentD = argument.parse("true");

        assertEquals(false, parsedArgumentA.output());
        assertEquals(false, parsedArgumentB.output());
        assertEquals(false, parsedArgumentC.output());
        assertEquals(true, parsedArgumentD.output());
    }

    @Test
    public void testBrigadierParserSuccess() {
        BoolArgument argument = BoolArgument.of("identifier", "description", false);
        StringReader reader = new StringReader("true");

        assertEquals(true, argument.parse(reader));
    }

    @Test
    public void testBrigadierParserFailureWithDefault() {
        BoolArgument argument = BoolArgument.of("identifier", "description", false);
        StringReader readerA = new StringReader("false");
        StringReader readerB = new StringReader("true");

        assertEquals(false, argument.parse(readerA));
        assertEquals(true, argument.parse(readerB));
    }

}
