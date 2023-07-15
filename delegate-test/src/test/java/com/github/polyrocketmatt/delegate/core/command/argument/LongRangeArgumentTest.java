package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.range.DelegateLongRange;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.LongMaxRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.LongMinRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongRangeArgumentTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    @Test
    public void testPrimaryConstructor() {
        LongRangeArgument argument = LongRangeArgument.of("identifier", "description");

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(DelegateLongRange.class, argument.getArgumentType());
    }

    @Test
    public void testSecondaryConstructor() {
        DelegateLongRange range = new DelegateLongRange(0L, 1L);
        LongRangeArgument argument = LongRangeArgument.of("identifier", "description", range);

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(DelegateLongRange.class, argument.getArgumentType());
        assertEquals(range, argument.getDefault().output());
    }

    @Test
    public void testTernaryConstructor() {
        LongRangeArgument argument = LongRangeArgument.of("identifier", "description", new NonNullRule());

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(DelegateLongRange.class, argument.getArgumentType());
        assertEquals(1, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof NonNullRule);
        assertNull(argument.getDefault().output());
    }

    @Test
    public void testQuaternaryConstructor() {
        DelegateLongRange range = new DelegateLongRange(0L, 1L);
        LongRangeArgument argument = LongRangeArgument.of("identifier", "description", range,
                new LongMinRule(0), new LongMaxRule(1));

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(DelegateLongRange.class, argument.getArgumentType());
        assertEquals(2, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof LongMinRule);
        assertTrue(argument.getArgumentRules().get(1) instanceof LongMaxRule);
        assertEquals(range, argument.getDefault().output());
    }

    @Test
    public void testInternalParserSuccess() {
        DelegateLongRange range = new DelegateLongRange(0L, 1L);
        LongRangeArgument argument = LongRangeArgument.of("identifier", "description", range);
        Argument<DelegateLongRange> parsedArgumentA = argument.parse("0-1");
        Argument<DelegateLongRange> parsedArgumentB = argument.parse("0-3");

        assertEquals(new DelegateLongRange(0L, 1L), parsedArgumentA.output());
        assertEquals(new DelegateLongRange(0L, 3L), parsedArgumentB.output());
    }

    @Test
    public void testInternalParserInputNull() {
        DelegateLongRange range = new DelegateLongRange(0L, 1L);
        LongRangeArgument argumentA = LongRangeArgument.of("identifier", "description");
        LongRangeArgument argumentB = LongRangeArgument.of("identifier", "description", range);

        assertThrows(ArgumentParseException.class, () -> argumentA.parse((String) null));
        assertEquals(range, argumentB.parse((String) null).output());
    }

    @Test
    public void testInternalParserFailure() {
        LongRangeArgument argument = LongRangeArgument.of("identifier", "description");

        assertThrows(ArgumentParseException.class, () -> argument.parse("0."));
        assertThrows(ArgumentParseException.class, () -> argument.parse("not a range"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0_0"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0L_0"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0--0"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0-0-1"));
    }

    @Test
    public void testInternalParserFailureWithDefault() {
        DelegateLongRange range = new DelegateLongRange(0L, 1L);
        LongRangeArgument argument = LongRangeArgument.of("identifier", "description", range);
        Argument<DelegateLongRange> parsedArgumentA = argument.parse("0.");
        Argument<DelegateLongRange> parsedArgumentB = argument.parse("not a number");
        Argument<DelegateLongRange> parsedArgumentC = argument.parse("0_0");
        Argument<DelegateLongRange> parsedArgumentD = argument.parse("0L_0");
        Argument<DelegateLongRange> parsedArgumentE = argument.parse("0--0");
        Argument<DelegateLongRange> parsedArgumentF = argument.parse("0-0-1");

        assertEquals(range, parsedArgumentA.output());
        assertEquals(range, parsedArgumentB.output());
        assertEquals(range, parsedArgumentC.output());
        assertEquals(range, parsedArgumentD.output());
        assertEquals(range, parsedArgumentE.output());
        assertEquals(range, parsedArgumentF.output());
    }

    @Test
    public void testBrigadierParserSuccess() {
        DelegateLongRange range = new DelegateLongRange(0L, 1L);
        LongRangeArgument argument = LongRangeArgument.of("identifier", "description", range);
        StringReader reader = new StringReader("0-2");

        assertEquals(new DelegateLongRange(0L, 2L), argument.parse(reader));
    }

    @Test
    public void testBrigadierParserFailure() {
        LongRangeArgument argument = LongRangeArgument.of("identifier", "description");
        StringReader readerA = new StringReader("0.");
        StringReader readerB = new StringReader("not a number");
        StringReader readerC = new StringReader("0_0");
        StringReader readerD = new StringReader("0L_0");
        StringReader readerE = new StringReader("0--0");
        StringReader readerF = new StringReader("0-0-1");

        assertThrows(ArgumentParseException.class, () -> argument.parse(readerA));
        assertThrows(ArgumentParseException.class, () -> argument.parse(readerB));
        assertThrows(ArgumentParseException.class, () -> argument.parse(readerC));
        assertThrows(ArgumentParseException.class, () -> argument.parse(readerD));
        assertThrows(ArgumentParseException.class, () -> argument.parse(readerE));
        assertThrows(ArgumentParseException.class, () -> argument.parse(readerF));
    }

    @Test
    public void testBrigadierParserFailureWithDefault() {
        DelegateLongRange range = new DelegateLongRange(0L, 1L);
        LongRangeArgument argument = LongRangeArgument.of("identifier", "description", range);
        StringReader readerA = new StringReader("0.");
        StringReader readerB = new StringReader("not a number");
        StringReader readerC = new StringReader("0_0");
        StringReader readerD = new StringReader("0L_0");
        StringReader readerE = new StringReader("0--0");
        StringReader readerF = new StringReader("0-0-1");

        assertEquals(range, argument.parse(readerA));
        assertEquals(range, argument.parse(readerB));
        assertEquals(range, argument.parse(readerC));
        assertEquals(range, argument.parse(readerD));
        assertEquals(range, argument.parse(readerE));
        assertEquals(range, argument.parse(readerF));
    }

}
