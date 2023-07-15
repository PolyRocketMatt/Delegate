package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.argument.range.DelegateDoubleRange;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.DoubleMaxRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.DoubleMinRule;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.NonNullRule;
import com.mojang.brigadier.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoubleRangeArgumentTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    @Test
    public void testPrimaryConstructor() {
        DoubleRangeArgument argument = DoubleRangeArgument.of("identifier", "description");

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(DelegateDoubleRange.class, argument.getArgumentType());
    }

    @Test
    public void testSecondaryConstructor() {
        DelegateDoubleRange range = new DelegateDoubleRange(0.0, 1.0);
        DoubleRangeArgument argument = DoubleRangeArgument.of("identifier", "description", range);

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(DelegateDoubleRange.class, argument.getArgumentType());
        assertEquals(range, argument.getDefault().output());
    }

    @Test
    public void testTernaryConstructor() {
        DoubleRangeArgument argument = DoubleRangeArgument.of("identifier", "description", new NonNullRule());

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(DelegateDoubleRange.class, argument.getArgumentType());
        assertEquals(1, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof NonNullRule);
        assertNull(argument.getDefault().output());
    }

    @Test
    public void testQuaternaryConstructor() {
        DelegateDoubleRange range = new DelegateDoubleRange(0.0, 1.0);
        DoubleRangeArgument argument = DoubleRangeArgument.of("identifier", "description", range,
                new DoubleMinRule(0.0), new DoubleMaxRule(1.0));

        assertEquals("identifier", argument.getIdentifier());
        assertEquals("description", argument.getArgumentDescription());
        assertEquals(AttributeType.ARGUMENT, argument.getType());
        assertEquals(DelegateDoubleRange.class, argument.getArgumentType());
        assertEquals(2, argument.getArgumentRules().size());
        assertTrue(argument.getArgumentRules().get(0) instanceof DoubleMinRule);
        assertTrue(argument.getArgumentRules().get(1) instanceof DoubleMaxRule);
        assertEquals(range, argument.getDefault().output());
    }

    @Test
    public void testInternalParserSuccess() {
        DelegateDoubleRange range = new DelegateDoubleRange(0.0, 1.0);
        DoubleRangeArgument argument = DoubleRangeArgument.of("identifier", "description", range);
        Argument<DelegateDoubleRange> parsedArgumentA = argument.parse("0.0-1.0");
        Argument<DelegateDoubleRange> parsedArgumentB = argument.parse("0.1-3.14");

        assertEquals(new DelegateDoubleRange(0.0, 1.0), parsedArgumentA.output());
        assertEquals(new DelegateDoubleRange(0.1, 3.14), parsedArgumentB.output());
    }

    @Test
    public void testInternalParserInputNull() {
        DelegateDoubleRange range = new DelegateDoubleRange(0.0, 1.0);
        DoubleRangeArgument argumentA = DoubleRangeArgument.of("identifier", "description");
        DoubleRangeArgument argumentB = DoubleRangeArgument.of("identifier", "description", range);

        assertThrows(ArgumentParseException.class, () -> argumentA.parse((String) null));
        assertEquals(range, argumentB.parse((String) null).output());
    }

    @Test
    public void testInternalParserFailure() {
        DoubleRangeArgument argument = DoubleRangeArgument.of("identifier", "description");

        assertThrows(ArgumentParseException.class, () -> argument.parse("0."));
        assertThrows(ArgumentParseException.class, () -> argument.parse("not a range"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0_0"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0L_0"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0--0"));
        assertThrows(ArgumentParseException.class, () -> argument.parse("0-0-1"));
    }

    @Test
    public void testInternalParserFailureWithDefault() {
        DelegateDoubleRange range = new DelegateDoubleRange(0.0, 1.0);
        DoubleRangeArgument argument = DoubleRangeArgument.of("identifier", "description", range);
        Argument<DelegateDoubleRange> parsedArgumentA = argument.parse("0.");
        Argument<DelegateDoubleRange> parsedArgumentB = argument.parse("not a number");
        Argument<DelegateDoubleRange> parsedArgumentC = argument.parse("0_0");
        Argument<DelegateDoubleRange> parsedArgumentD = argument.parse("0L_0");
        Argument<DelegateDoubleRange> parsedArgumentE = argument.parse("0--0");
        Argument<DelegateDoubleRange> parsedArgumentF = argument.parse("0-0-1");

        assertEquals(range, parsedArgumentA.output());
        assertEquals(range, parsedArgumentB.output());
        assertEquals(range, parsedArgumentC.output());
        assertEquals(range, parsedArgumentD.output());
        assertEquals(range, parsedArgumentE.output());
        assertEquals(range, parsedArgumentF.output());
    }

    @Test
    public void testBrigadierParserSuccess() {
        DelegateDoubleRange range = new DelegateDoubleRange(0.0, 1.0);
        DoubleRangeArgument argument = DoubleRangeArgument.of("identifier", "description", range);
        StringReader reader = new StringReader("0.0-2.0");

        assertEquals(new DelegateDoubleRange(0.0, 2.0), argument.parse(reader));
    }

    @Test
    public void testBrigadierParserFailure() {
        DoubleRangeArgument argument = DoubleRangeArgument.of("identifier", "description");
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
        DelegateDoubleRange range = new DelegateDoubleRange(0.0, 1.0);
        DoubleRangeArgument argument = DoubleRangeArgument.of("identifier", "description", range);
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
